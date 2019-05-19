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

import adminbean.Order;
import api.DataLink;

public class SelectOrder extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public SelectOrder() {
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
		ArrayList<Order> orderList = new ArrayList<Order>();
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from shop order by id desc");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Order order = new Order();
				order.setId(rs.getString(1));
				order.setUser(rs.getString(2));
				order.setSku(rs.getInt(3));
				order.setGoodsName(rs.getString(4));
				order.setCategoryName(rs.getString(5));
				order.setBrandName(rs.getString(6));
				order.setStorage(rs.getString(7));
				order.setColor(rs.getString(8));
				order.setScreen(rs.getString(9));
				order.setNum(rs.getInt(10));
				order.setUnitPrice(rs.getFloat(11));
				order.setTotalPrice(rs.getFloat(12));
				order.setPay(rs.getBoolean(13));
				order.setCreateTime(rs.getString(14));
				order.setPaymentTime(rs.getString(15));
				orderList.add(order);
			}
			json.put("status", "success");
			json.put("code","0");
			json.put("message", orderList);
			out.write(json.toString());
			out.flush();
			out.close();
			stmt.close();
			rs.close();
			conn.close();
			
		} catch (Exception e) {
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
