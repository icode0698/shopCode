package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import api.DataLink;

public class NowValue extends HttpServlet {
	public static int storageID = 31000000;
	public static int colorID = 32000000;
	public static int screenID = 33000000;
	/**
		 * Constructor of the object.
		 */
	public NowValue() {
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
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		int storageNowID = 0;
		int colorNowID = 0;
		int screenNowID = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs= null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("select max(valueID) from parametervalue where parameterID=?");
			stmt.setInt(1, storageID);
			rs = stmt.executeQuery();
			while(rs.next()){
				storageNowID = rs.getInt(1);
			}
			json.put("status", "success");
			json.put("storageNowID", storageNowID);
			stmt = conn.prepareStatement("select max(valueID) from parametervalue where parameterID=?");
			stmt.setInt(1, colorID);
			rs = stmt.executeQuery();
			while(rs.next()){
				colorNowID = rs.getInt(1);
			}
			json.put("colorNowID", colorNowID);
			stmt = conn.prepareStatement("select max(valueID) from parametervalue where parameterID=?");
			stmt.setInt(1, screenID);
			rs = stmt.executeQuery();
			while(rs.next()){
				screenNowID = rs.getInt(1);
			}
			json.put("screenNowID", screenNowID);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			stmt.close();
			conn.close();
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
