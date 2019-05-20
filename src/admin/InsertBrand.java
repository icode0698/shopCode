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

public class InsertBrand extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public InsertBrand() {
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
		int brandID = Integer.parseInt(request.getParameter("brandID"));
		String brandName = request.getParameter("brandName");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("insert into brand(brandID,brandName) values(?,?)");
			stmt.setInt(1, brandID);
			stmt.setString(2, brandName);
			int index = stmt.executeUpdate();
			if(index>0){
				json.put("status", "success");
				json.put("message", "品牌信息添加成功");
			}
			else{
				json.put("status", "fail");
				json.put("message", "品牌信息添加出现错误");
			}
			out.write(json.toString());
			out.flush();
			out.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", "fail");
			json.put("message", "BrandID重复");
			System.out.println(json.toString());
			out.write(json.toString());
			out.flush();
			out.close();
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
