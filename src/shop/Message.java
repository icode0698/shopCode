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

public class Message extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Message() {
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
		String message = request.getParameter("message");
		System.out.println(message);
		DataLink dataLink = new DataLink();
		Connection conn;
		PreparedStatement stmt;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			conn = dataLink.linkData();
			stmt = conn.prepareStatement("insert into message(user, message) values(?, ?)");
			stmt.setString(1, user);
			stmt.setString(2, message);
			int index = stmt.executeUpdate();
			if(index>0){
				json.put("status", "success");
				json.put("message", "留言成功，感谢您的留言，我们会认真对待。");
			}
			else{
				json.put("status", "fail");
				json.put("message", "抱歉，数据库操作异常");
			}
			out.write(json.toString());
			out.flush();
			out.close();
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
