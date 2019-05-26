package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import api.DataLink;
import api.LastTime;

public class OffLine extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public OffLine() {
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
		DataLink dataLink = new DataLink();
		Connection conn = null;
		PreparedStatement stmt = null;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			conn = dataLink.linkData();
			stmt = conn.prepareStatement("update user set online=? where user=?");
			stmt.setBoolean(1, false);
			stmt.setString(2, user);
			int index = stmt.executeUpdate();
			if(index>0){
//				request.getSession().removeAttribute("user");
//				LastTime lastTime = new LastTime(request.getSession());
//				lastTime.update();
				json.put("status", "success");
				json.put("message", "用户下线成功");
			}
			else{
				json.put("status", "fail");
				json.put("message", "操作出现错误");
			}
			stmt.close();
			conn.close();
			out.write(json.toString());
			out.flush();
			out.close();
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
