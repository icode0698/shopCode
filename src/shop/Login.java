package shop;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import api.DataLink;

public class Login extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Login() {
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
		//System.out.println("TomcatReloadableYes");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		String sqlStr = "";
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		System.out.println("user&&password&&type:"+user+"&&"+password+"&&"+type);
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//HashMap<String, String> map = new HashMap<String, String>(); 
		/*PrintWriter out = response.getWriter();
		map.put("type", type+"_servlet");
		map.put("status", "successful");
		map.put("user", user+"_servlet");
		System.out.println("HashMap_servlet:"+map);*/
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try{
			pstmt = conn.prepareStatement("select password,online from user where user=?");
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(password.equals(rs.getString(1))&&rs.getBoolean(2)==false){
					json.put("status", "success");
					json.put("user", user);
					json.put("message", "登录成功");
					session.setAttribute("user", user);
					rs.close();
					pstmt = conn.prepareStatement("update user set currentTime=?, online=? where user=?");
					pstmt.setString(1, df.format(new Date()));
					pstmt.setBoolean(2, true);
					pstmt.setString(3, user);
					pstmt.executeUpdate();
					pstmt = conn.prepareStatement("select viewCount from user where user=?");
					pstmt.setString(1, user);
					rs = pstmt.executeQuery();
					while(rs.next()){
						int count = rs.getInt(1);
						count++;
						pstmt = conn.prepareStatement("update user set viewCount=? where user=?");
						pstmt.setInt(1, count);
						pstmt.setString(2, user);
						pstmt.executeUpdate();
					}
					//System.out.println("json.status:"+json.getString("status"));
					//System.out.println("json.user:"+json.getString("user"));
					//System.out.println("json_servlet_json:"+json);
					//System.out.println("json_servlet_toString:"+json.toString());
					out.write(json.toString());
					out.flush();
					out.close();
				}	
				else if(rs.getBoolean(2)==true){
					json.put("status", "error");
					json.put("user", user);
					json.put("message", "当前用户已处于在线状态");
					out.write(json.toString());
					out.flush();
					out.close();
				}
				else{
					json.put("status", "error");
					json.put("user", user);
					json.put("message", "UserID或Password不正确");
					out.write(json.toString());
					out.flush();
					out.close();
				}
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