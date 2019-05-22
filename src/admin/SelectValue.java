package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import adminbean.Value;
import api.DataLink;

public class SelectValue extends HttpServlet {
	public static int storageID = 31000000;
	public static int colorID = 32000000;
	public static int screenID = 33000000;
	/**
		 * Constructor of the object.
		 */
	public SelectValue() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		ArrayList<Value> storageList = new ArrayList<Value>();
		ArrayList<Value> colorList = new ArrayList<Value>();
		ArrayList<Value> screenList = new ArrayList<Value>();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		Connection conn = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		ResultSet rs = null;
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("select valueID, value from parametervalue where parameterID=?");
			stmt.setInt(1, storageID);
			rs = stmt.executeQuery();
			while(rs.next()){
				Value storageValue = new Value();
				storageValue.setId(rs.getInt(1));
				storageValue.setName(rs.getString(2));
				storageList.add(storageValue);
			}
			json.put("status", "success");
			json.put("storageList", storageList);
			stmt = conn.prepareStatement("select valueID, value from parametervalue where parameterID=?");
			stmt.setInt(1, colorID);
			rs = stmt.executeQuery();
			while(rs.next()){
				Value colorValue = new Value();
				colorValue.setId(rs.getInt(1));
				colorValue.setName(rs.getString(2));
				colorList.add(colorValue);
			}
			json.put("colorList", colorList);
			stmt = conn.prepareStatement("select valueID, value from parametervalue where parameterID=?");
			stmt.setInt(1, screenID);
			rs = stmt.executeQuery();
			while(rs.next()){
				Value screenValue = new Value();
				screenValue.setId(rs.getInt(1));
				screenValue.setName(rs.getString(2));
				screenList.add(screenValue);
			}
			json.put("screenList", screenList);
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}
