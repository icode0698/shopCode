package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import adminbean.Price;
import api.DataLink;

public class InsertPrice extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public InsertPrice() {
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
		ArrayList<Price> priceList = new ArrayList<Price>();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtIn = null;
		DecimalFormat df = new DecimalFormat("0.00");
		DataLink dataLink = new DataLink();
		ResultSet rs = null;
		ResultSet rsIn = null;
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("select * from price where price=?");
			stmt.setFloat(1, 0.00f);
			rs = stmt.executeQuery();
			while(rs.next()){
				Price price = new Price();
				price.setSku(rs.getInt(1));
				stmtIn = conn.prepareStatement("select goodsName from goods where goodsID=?");
				stmtIn.setInt(1, rs.getInt(2));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					price.setGoodsName(rsIn.getString(1));
				}
				stmtIn = conn.prepareStatement("select value from parametervalue, goodsvalue "
						+ "where parametervalue.valueID = goodsvalue.valueID and  goodsvalue.spID = ?");
				stmtIn.setInt(1, rs.getInt(3));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					price.setStorage(rsIn.getString(1));
				}
				stmtIn.setInt(1, rs.getInt(4));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					price.setColor(rsIn.getString(1));
				}
				stmtIn.setInt(1, rs.getInt(5));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					price.setScreen(rsIn.getString(1));
				}
				price.setUnitPrice(df.format(rs.getFloat(6)));
				price.setStock(rs.getInt(7));
				priceList.add(price);
			}
			json.put("status", "success");
			json.put("message", priceList);
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", "fail");
			json.put("message", "查询出错");
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
