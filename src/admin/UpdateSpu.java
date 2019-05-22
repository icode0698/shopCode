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

public class UpdateSpu extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public UpdateSpu() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		String data = request.getParameter("data");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		Connection conn = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			if(type==null||"".equals(type)){
				json.put("status", "fail");
				json.put("message", "数据请求的格式不正确");
			}
			else{
				if("name".equals(type)){
					stmt = conn.prepareStatement("update goods set goodsName=? where goodsID=?");
					stmt.setString(1, data);
					stmt.setInt(2, id);
					int index = stmt.executeUpdate();
					if(index>0){
						json.put("status", "success");
						json.put("message", "商品名称更新成功");
					}
					else{
						json.put("status", "fail");
						json.put("message", "商品名称更新出现错误");
					}
				}
				if("category".equals(type)){
					ResultSet rs = null;
					int categoryID = 0;
					stmt = conn.prepareStatement("select categoryID from category where categoryName=?");
					stmt.setString(1, data);
					rs = stmt.executeQuery();
					while(rs.next()){
						categoryID = rs.getInt(1);
					}
					if(categoryID==0){
						json.put("status", "fail");
						json.put("message", "请输入正确的分类名称");
					}
					else{
						stmt = conn.prepareStatement("update goods set categoryID=? where goodsID=?");
						stmt.setInt(1, categoryID);
						stmt.setInt(2, id);
						int index = stmt.executeUpdate();
						if(index>0){
							json.put("status", "success");
							json.put("message", "商品所属分类更新成功");
						}
						else{
							json.put("status", "fail");
							json.put("message", "商品所属分类更新出现错误");
						}
					}
				}
				if("brand".equals(type)){
					ResultSet rs = null;
					int brandID = 0;
					stmt = conn.prepareStatement("select brandID from brand where brandName=?");
					stmt.setString(1, data);
					rs = stmt.executeQuery();
					while(rs.next()){
						brandID = rs.getInt(1);
					}
					if(brandID==0){
						json.put("status", "fail");
						json.put("message", "请输入正确的品牌名称");
					}
					else{
						stmt = conn.prepareStatement("update goods set brandID=? where goodsID=?");
						stmt.setInt(1, brandID);
						stmt.setInt(2, id);
						int index = stmt.executeUpdate();
						if(index>0){
							json.put("status", "success");
							json.put("message", "商品所属品牌更新成功");
						}
						else{
							json.put("status", "fail");
							json.put("message", "商品所属品牌更新出现错误");
						}
					}
				}
			}
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", "fail");
			json.put("message", "商品SPU信息更新出现错误");
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
