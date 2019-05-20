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

public class InsertValue extends HttpServlet {
	public static int storageID = 31000000;
	public static int colorID = 32000000;
	public static int screenID = 33000000;
	/**
		 * Constructor of the object.
		 */
	public InsertValue() {
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
		String message = request.getParameter("message");
        JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		Connection conn = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		if(message==null||"".equals(message)){
			json.put("status", "fail");
			json.put("message", "数据请求格式错误");
			out.write(json.toString());
			out.flush();
			out.close();
		}
		else{
			if("storage".equals(message)){
				int ID = Integer.parseInt(request.getParameter("storageID"));
		        String storageName = request.getParameter("storageName");
		        try {
					stmt = conn.prepareStatement("insert into parametervalue values(?,?,?)");
					stmt.setInt(1, ID);
					stmt.setInt(2, storageID);
					stmt.setString(3, storageName);
					int index = stmt.executeUpdate();
					if(index>0){
						json.put("status", "success");
						json.put("message", "商品存储属性添加成功");
					}
					else {
						json.put("status", "fail");
						json.put("message", "商品存储属性添加出错");
					}
					out.write(json.toString());
					out.flush();
					out.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					json.put("status", "fail");
					json.put("message", "存储ID重复");
					out.write(json.toString());
					out.flush();
					out.close();
				}
			}
			if("color".equals(message)){
		        int ID = Integer.parseInt(request.getParameter("colorID"));
		        String colorName = request.getParameter("colorName");
		        try {
		        	stmt = conn.prepareStatement("insert into parametervalue values(?,?,?)");
					stmt.setInt(1, ID);
					stmt.setInt(2,colorID);
					stmt.setString(3, colorName);
					int index = stmt.executeUpdate();
					if(index>0){
						json.put("status", "success");
						json.put("message", "商品颜色属性添加成功");
					}
					else {
						json.put("status", "fail");
						json.put("message", "商品颜色属性添加出错");
					}
					out.write(json.toString());
					out.flush();
					out.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					json.put("status", "fail");
					json.put("message", "颜色ID重复");
					out.write(json.toString());
					out.flush();
					out.close();
				}
			}
			if("screen".equals(message)){
		        int ID = Integer.parseInt(request.getParameter("screenID"));
		        String screenName = request.getParameter("screenName");
		        try {
		        	stmt = conn.prepareStatement("insert into parametervalue values(?,?,?)");
					stmt.setInt(1, ID);
					stmt.setInt(2, screenID);
					stmt.setString(3, screenName);
					int index = stmt.executeUpdate();
					if(index>0){
						json.put("status", "success");
						json.put("message", "商品屏幕尺寸属性添加成功");
					}
					else {
						json.put("status", "fail");
						json.put("message", "商品屏幕尺寸属性添加出错");
					}
					out.write(json.toString());
					out.flush();
					out.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					json.put("status", "fail");
					json.put("message", "尺寸ID重复");
					out.write(json.toString());
					out.flush();
					out.close();
				}
			}
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
