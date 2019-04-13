package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Register extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Register() {
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
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sqlStr = "";
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try{
			DataLink dataLink = new DataLink();
			conn = dataLink.linkData();
			pstmt = conn.prepareStatement("select count(*) as count from user where user=?");
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			System.out.println("count:"+count);
			if(count==0){
				pstmt = conn.prepareStatement("insert into user value(?, ?)");
				pstmt.setString(1, user);
				pstmt.setString(2, password);
				pstmt.execute();
				json.put("status", "success");
				json.put("message", "注册成功");
				out.write(json.toString());
				out.flush();
				out.close();
			}
			else{
				json.put("status", "error");
				json.put("message", "抱歉，该用户名已注册");
				out.write(json.toString());
				out.flush();
				out.close();
				return;
			}
		}catch (SQLException e) {System.out.println(e.getMessage());}
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
