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
import api.GoodsInfo;

public class Join extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Join() {
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
		int goodsID = Integer.parseInt(request.getParameter("goodsID"));
		String goodsName = request.getParameter("goodsName");
		String brandName = request.getParameter("brandName");
		String storage = request.getParameter("storage");
		String color = request.getParameter("color");
		String screen = request.getParameter("screen");
		int num = Integer.parseInt(request.getParameter("num"));
		GoodsInfo goodsInfo = new GoodsInfo(goodsID, color, screen, storage);
		String categoryName = "";
		String user = request.getSession().getAttribute("user").toString();
		int sku = goodsInfo.getSku();
		float price = goodsInfo.getPrice();
		System.out.println("goodsID&&brandName&&storage&&color&&screen&&num&&sku"+goodsID+brandName+storage+color+screen+num+sku);
		DataLink dataLink = new DataLink();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			conn = dataLink.linkData();
			stmt = conn.prepareStatement("select categoryName from goods, category where goods.categoryID=category.categoryID and goods.goodsID=?");
			stmt.setInt(1, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				categoryName = rs.getString(1);
			}
			stmt = conn.prepareStatement("select count(*) as count, num from shop where user=? and sku=? and isPay=?");
			stmt.setString(1, user);
			stmt.setInt(2, sku);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			int addNum = rs.getInt(2);
			if(count==0){
				stmt = conn.prepareStatement("insert into shop(user, sku, goodsName, categoryName, brandName, storage, "
						+ "color, screen, num, unitPrice, totalPrice, isPay) values(?,?,?,?,?,?,?,?,?,?,?,?)");
				stmt.setString(1, user);
				stmt.setInt(2, sku);
				stmt.setString(3, goodsName);
				stmt.setString(4, categoryName);
				stmt.setString(5, brandName);
				stmt.setString(6, storage);
				stmt.setString(7, color);
				stmt.setString(8, screen);
				stmt.setInt(9, num);
				stmt.setFloat(10, price);
				stmt.setFloat(11, num*price);
				stmt.setBoolean(12, false);
				stmt.executeUpdate();
			}
			else {
				stmt = conn.prepareStatement("update shop set num=? where user=? and sku=?");
				stmt.setInt(1, num+addNum);
				stmt.setString(2, user);
				stmt.setInt(3, sku);
				stmt.executeUpdate();
			}
			json.put("status", "success");
			json.put("message", "添加成功");
			out.write(json.toString());
			rs.close();
			stmt.close();
			conn.close();
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
