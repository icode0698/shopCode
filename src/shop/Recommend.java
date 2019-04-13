package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.sun.mail.imap.protocol.Status;

import bean.RecommendBean;

public class Recommend extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Recommend() {
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
		Connection conn = dataLink.linkData();
		PreparedStatement stmt = null;
		PreparedStatement stmtIn = null;
		ResultSet rs = null;
		ResultSet rsIn = null;
		String goodsName;
		String categoryName = request.getParameter("category");
		ArrayList<RecommendBean> itemList = new ArrayList<RecommendBean>();
		int goodsID;
		int categoryID = 0;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try{
			stmt = conn.prepareStatement("select categoryID from category where categoryName=?");
			stmt.setString(1, categoryName);
			rs = stmt.executeQuery();
			while(rs.next()){
				categoryID = rs.getInt(1);
//				System.out.println("ItemType_categoryName:"+categoryID);
			}
			stmt = conn.prepareStatement("select goodsID, goodsName from goods where categoryID=?");
			stmt.setInt(1, categoryID);
			rs = stmt.executeQuery();
			while(rs.next()){
				ArrayList<String> imgList = new ArrayList<String>();
				RecommendBean recommendBean = new RecommendBean();
				recommendBean.setCategoryName(categoryName);
				goodsID = rs.getInt(1);
				recommendBean.setGoodsID(goodsID);
				goodsName = rs.getString(2);
				recommendBean.setGoodsName(goodsName);
				stmtIn = conn.prepareStatement("select imgSrc from img where goodsID=?");
				stmtIn.setInt(1, goodsID);
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					imgList.add(rsIn.getString(1));
					recommendBean.setImgList(imgList);
				}
				itemList.add(recommendBean);
			}
			json.put("status", "success");
			json.put("message", itemList);
			//System.out.println("+++++++++++++++++++++RecommendItemList:"+json.get("status")+"&&"+json.get("message")+"&&"+json);
			out.write(json.toString());
			out.flush();
			out.close();
		}catch(SQLException e){
			e.getMessage();
			json.put("status","failed");
			json.put("message", "服务器异常");
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
