package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import api.DataLink;
import api.GoodsImg;
import api.SkuStock;
import api.SkuToSpu;
import bean.TrolleyBean;

public class Trolley extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Trolley() {
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
		DataLink dataLink = new DataLink();
		Connection conn;
		ResultSet rs;
		PreparedStatement stmt;
		ArrayList<TrolleyBean> itemList = new ArrayList<TrolleyBean>();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("user");
		System.out.println(user);
		if("".equals(user)||user==null){
			json.put("status", "fail");
			json.put("code", "0");
			json.put("message", "用户未登录或登录会话已超时");
			out.write(json.toString());
			out.flush();
			out.close();
			return;
		}
		conn = dataLink.linkData();
		try{
			stmt = conn.prepareStatement("select id, user, sku, storage, color, screen, num, isPay, unitPrice, totalPrice, goodsName from shop where user=? and isPay=0");
			stmt.setString(1, user);
			rs = stmt.executeQuery();
			while(rs.next()){
				TrolleyBean trolleyBean = new TrolleyBean();
				trolleyBean.setId(rs.getString(1));
				trolleyBean.setUser(rs.getString(2));
				int sku = rs.getInt(3);
				trolleyBean.setSku(sku);
				SkuStock skuStock = new SkuStock(sku);
				trolleyBean.setStock(skuStock.getStock());
				SkuToSpu skuToSpu = new SkuToSpu(sku);
				GoodsImg goodsImg = new GoodsImg(skuToSpu.getSpu());
				trolleyBean.setImgList(goodsImg.getImg());
				trolleyBean.setStorage(rs.getString(4));
				trolleyBean.setColor(rs.getString(5));
				trolleyBean.setScreen(rs.getString(6));
				trolleyBean.setNum(rs.getInt(7));
				trolleyBean.setPay(rs.getBoolean(8));
				trolleyBean.setUnitPrice(rs.getFloat(9));
				trolleyBean.setTotalPrice(rs.getFloat(10));
				trolleyBean.setGoodsName(rs.getString(11));
				//System.out.println(trolleyBean.toString());
				itemList.add(trolleyBean);
			}
			//System.out.println(itemList);
			json.put("status", "success");
			json.put("code", "1");
			json.put("message", itemList);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			stmt.close();
			conn.close();
		}catch (SQLException e) {
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
