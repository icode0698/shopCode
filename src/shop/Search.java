package shop;

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

import api.DataLink;
import api.SkuToSpu;
import bean.RecommendBean;

public class Search extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Search() {
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
		ArrayList<RecommendBean> itemList = new ArrayList<RecommendBean>();
		int goodsID = 0;
		String goodsName = "";
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtIn = null;
		ResultSet rs = null;
		ResultSet rsIn = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("select goodsID, goodsName from goods");
			rs = stmt.executeQuery();
			while(rs.next()){
				ArrayList<String> imgList = new ArrayList<String>();
				RecommendBean recommendBean = new RecommendBean();
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
			out.write(json.toString());
			out.flush();
			out.close();
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
