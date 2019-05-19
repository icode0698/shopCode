package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import api.DataLink;

public class Info extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Info() {
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
		String user = request.getSession().getAttribute("user").toString();
		String nickName = "";
		String lastTime = "";
		String headPic = "";
		String regTime = "";
		String viewCount = "";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		try {
			conn = dataLink.linkData();
			stmt = conn.prepareStatement("select nickName, lastTime, headPic, regTime, viewCount from user where user=?");
			stmt.setString(1, user);
			rs = stmt.executeQuery();
			while(rs.next()){
				nickName = rs.getString(1);
				lastTime = rs.getString(2);
				headPic = rs.getString(3);
				regTime = rs.getString(4);
				viewCount = rs.getString(5);
			}
			json.put("status", "success");
			json.put("user", user);
			json.put("nickName", nickName);
			json.put("lastTime", lastTime);
			json.put("headPic", headPic);
			json.put("regTime", regTime);
			json.put("viewCount", viewCount);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
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
