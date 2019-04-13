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

public class Price extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Price() {
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
		String color = request.getParameter("color");
		String screen = request.getParameter("screen");
		String storage = request.getParameter("storage");
		System.out.println("color&&screen&&storage&&goodsID"+color+screen+storage+goodsID);
		float price = 0;
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		try{
			stmt = conn.prepareStatement("select price from price where "
					+ "price.spID1 = (select spID from goodsvalue where goodsID=? and valueID = (select valueID from parametervalue where value=?)) "
					+ "and price.spID2 = (select spID from goodsvalue where goodsID=? and valueID = (select valueID from parametervalue where value=?)) "
					+ "and price.spID3 = (select spID from goodsvalue where goodsID=? and valueID = (select valueID from parametervalue where value=?))");
			stmt.setInt(1, goodsID);
			stmt.setString(2, storage);
			stmt.setInt(3, goodsID);
			stmt.setString(4, color);
			stmt.setInt(5, goodsID);
			stmt.setString(6, screen);
			//System.out.println("+++++++++++++++++++2333");
			rs = stmt.executeQuery();
			//System.out.println("+++++++++++++++++++23333333");
			//System.out.println(rs);
			while(rs.next()){
				price = rs.getFloat(1);
			}
			System.out.println("price:"+price);
			if(price!=0){
				json.put("status", "success");
				json.put("price", price);
				out.write(json.toString());
				out.flush();
				out.close();
			}
			else{
				json.put("status", "fail");
				json.put("price", price);
				out.write(json.toString());
				out.flush();
				out.close();
			}
		}catch (SQLException e) {e.printStackTrace();}
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
