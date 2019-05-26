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

import adminbean.Message;
import adminbean.User;
import api.DataLink;

public class Control extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Control() {
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
		String type = request.getParameter("type");
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Message> messageList = new ArrayList<Message>();
		ArrayList<User> userList = new ArrayList<User>();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if("".equals(type)){
			json.put("status", "fail");
			json.put("message", "数据的请求格式不对");
			
		}
		else if("message".equals(type)){
			int day = Integer.parseInt(request.getParameter("value"));
			try {
				if(day==0){
					stmt = conn.prepareStatement("select * from message order by createTime desc");
				}
				else{
					stmt = conn.prepareStatement("select * from message where DATE_SUB(CURDATE(), INTERVAL ? DAY) <= createTime order by createTime desc");
					stmt.setInt(1, day);
				}
				rs = stmt.executeQuery();
				while(rs.next()){
					Message message = new Message();
					message.setId(rs.getInt(1));
					message.setUser(rs.getString(2));
					PreparedStatement temp = conn.prepareStatement("select nickName from user where user=?");
					temp.setString(1, rs.getString(2));
					ResultSet tempRs = temp.executeQuery();
					while(tempRs.next()){
						message.setNickName(tempRs.getString(1));
					}
					message.setMessage(rs.getString(3));
					message.setTime(rs.getString(4));
					messageList.add(message);
				}
				json.put("status", "success");
				json.put("message", messageList);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		else if("user".equals(type)){
			int day = Integer.parseInt(request.getParameter("value"));
			try {
				if(day==0){
					stmt = conn.prepareStatement("select user, nickName, regTime, lastTime, online, viewCount from user order by regTime desc");
				}
				else{
					stmt = conn.prepareStatement("select user, nickName, regTime, lastTime, online, viewCount from user where DATE_SUB(CURDATE(), INTERVAL ? DAY) <= regTime order by regTime desc");
					stmt.setInt(1, day);
				}
				rs = stmt.executeQuery();
				while(rs.next()){
					User user = new User();
					user.setUser(rs.getString(1));
					user.setNickName(rs.getString(2));
					user.setTime(rs.getString(3));
					user.setLastTime(rs.getString(4));
					user.setOnline(rs.getBoolean(5));
					user.setViewCount(rs.getInt(6));
					userList.add(user);
				}
				json.put("status", "success");
				json.put("message", userList);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		out.write(json.toString());
		out.flush();
		out.close();
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
