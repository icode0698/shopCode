package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import api.DataLink;
import api.SkuStock;

public class Settlement extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Settlement() {
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
		JSONArray jsonArray = new JSONArray(request.getParameter("idList"));
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
//		System.out.println(request.getParameter("idList"));
//		System.out.println(jsonArray);
		for(int i=0;i<jsonArray.length();i++){
//			System.out.println(jsonArray.get(i));
			JSONObject jsonIndex = jsonArray.getJSONObject(i);
//			System.out.println(jsonIndex);
//			System.out.println(jsonIndex.get("id")+"&&"+jsonIndex.get("num"));
			String id = jsonIndex.get("id").toString();
			int num = Integer.parseInt(jsonIndex.get("num").toString());
			int sku = Integer.parseInt(jsonIndex.get("sku").toString());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				SkuStock skuStock = new SkuStock(sku);
				int reduceStockIndex = skuStock.reduceStock(num);
				if(reduceStockIndex==1){
					PreparedStatement stmt = conn.prepareStatement("update shop set isPay=?, num=?, paymentTime=? where id=?");
					stmt.setBoolean(1, true);
					stmt.setInt(2, num);
					String paymentTime = df.format(new Date());
					System.out.println(paymentTime);
					stmt.setString(3, paymentTime);
					stmt.setString(4, id);
					int index = stmt.executeUpdate();
					if(index>0){
						json.put("status", "success");
						json.put("message", "购物车结算成功");
						out.write(json.toString());
					}
					else{
						int increaseStockIndex = skuStock.increaseStock(num);
						if(increaseStockIndex==1){
							json.put("status", "fail");
							json.put("message", "结算时数据库操作异常，库存数量已回退");
							out.write(json.toString());
						}
						if(increaseStockIndex==0){
							json.put("status", "fail");
							json.put("message", "结算时数据库操作异常，回退库存数量时数据库操作异常");
							out.write(json.toString());
						}
					}
					stmt.close();
				}
				if(reduceStockIndex==0){
					json.put("status", "fail");
					json.put("message", "计算库存时数据库操作异常,未结算相关订单");
					out.write(json.toString());
				}
				out.flush();
				out.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
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
