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
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

//import com.sun.mail.handlers.message_rfc822;

import api.DataLink;

public class MoveOut extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public MoveOut() {
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
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("user");
		System.out.println("id&&user:"+id+"&&"+user);
		DataLink dataLink = new DataLink();
		Connection conn;
		PreparedStatement stmt;
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		conn = dataLink.linkData();
		try{
			stmt = conn.prepareStatement("delete from shop where id=? and user=?");
			stmt.setString(1, id);
			stmt.setString(2, user);
			int index = stmt.executeUpdate();
			System.out.println("index:"+index);
			if(index>0){
				json.put("status", "success");
				json.put("message", "已将商品移出我的购物车");
				out.write(json.toString());
			}
			else{
				json.put("status", "fail");
				json.put("message", "商品不存在，请刷新页面后重试");
				out.write(json.toString());
			}
			out.flush();
			out.close();
			stmt.close();
			conn.close();
		}catch (SQLException e) {
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
