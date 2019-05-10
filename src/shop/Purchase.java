package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import api.DataLink;
import api.GoodsInfo;
import api.SkuStock;

public class Purchase extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Purchase() {
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
			SkuStock skuStock = new SkuStock(sku);
			int reduceStockIndex = skuStock.reduceStock(num);
			if(reduceStockIndex==1){
				stmt = conn.prepareStatement("insert into shop(id, user, sku, goodsName, categoryName, brandName, storage, "
						+ "color, screen, num, unitPrice, totalPrice, isPay, paymentTime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String id = df.format(new Date());
				stmt.setString(1, id);
				stmt.setString(2, user);
				stmt.setInt(3, sku);
				stmt.setString(4, goodsName);
				stmt.setString(5, categoryName);
				stmt.setString(6, brandName);
				stmt.setString(7, storage);
				stmt.setString(8, color);
				stmt.setString(9, screen);
				stmt.setInt(10, num);
				stmt.setFloat(11, price);
				stmt.setFloat(12, num*price);
				stmt.setBoolean(13, true);
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String paymentTime = df.format(new Date());
				stmt.setString(14, paymentTime);
				int payIndex = stmt.executeUpdate();
				if(payIndex>0){
					json.put("status", "success");
					json.put("message", "购买成功，感谢您的支持");
					out.write(json.toString());
				}
				else{
					int increaseStockIndex = skuStock.increaseStock(num);
					if(increaseStockIndex==1){
						json.put("status", "fail");
						json.put("message", "支付时数据库操作异常，库存数量已回退");
						out.write(json.toString());
					}
					if(increaseStockIndex==0){
						json.put("status", "fail");
						json.put("message", "结算时数据库操作异常，回退库存数量时数据库操作异常");
						out.write(json.toString());
					}
				}
			}
			else{
				json.put("status", "fail");
				json.put("message", "计算库存时数据库操作异常");
				out.write(json.toString());
			}
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
