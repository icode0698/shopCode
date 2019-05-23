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
import bean.RecommendBean;

public class SearchValue extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public SearchValue() {
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

		doPost(request, response);request.setCharacterEncoding("UTF-8");
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
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String value = request.getParameter("value");
		System.out.println("+++++++++++"+value);
		ArrayList<RecommendBean> itemList = new ArrayList<RecommendBean>();
		int categoryID = 0;
		int brandID = 0;
		int categoryNum = 0;
		int brandNum = 0;
		int goodsNum = 0;
		int goodsID = 0;
		String goodsName = "";
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtIn = null;
		ResultSet rs = null;
		ResultSet rsTemp = null;
		ResultSet rsIn = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("select count(*) from category where categoryName like ?");
			stmt.setString(1, '%'+value+'%');
			rs = stmt.executeQuery();
			rs.next();
			categoryNum = rs.getInt(1);
			stmt = conn.prepareStatement("select count(*) from brand where brandName like ?");
			stmt.setString(1, '%'+value+'%');
			rs = stmt.executeQuery();
			rs.next();
			brandNum = rs.getInt(1);
			stmt = conn.prepareStatement("select count(*) from goods where upper(goodsName) like upper(?)");
			stmt.setString(1, '%'+value+'%');
			rs = stmt.executeQuery();
			rs.next();
			goodsNum = rs.getInt(1);
			if(categoryNum>0){
				stmt = conn.prepareStatement("select categoryID from category where categoryName like ?");
				stmt.setString(1, '%'+value+'%');
				rs = stmt.executeQuery();
				while(rs.next()){
					categoryID = rs.getInt(1);
					stmt = conn.prepareStatement("select goodsID, goodsName from goods where categoryID=?");
					stmt.setInt(1, categoryID);
					rsTemp = stmt.executeQuery();
					while(rsTemp.next()){
						ArrayList<String> imgList = new ArrayList<String>();
						RecommendBean recommendBean = new RecommendBean();
						goodsID = rsTemp.getInt(1);
						recommendBean.setGoodsID(goodsID);
						goodsName = rsTemp.getString(2);
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
				}
			}
			else if(brandNum>0){
				stmt = conn.prepareStatement("select brandID from brand where brandName like ?");
				stmt.setString(1, '%'+value+'%');
				rs = stmt.executeQuery();
				while(rs.next()){
					brandID = rs.getInt(1);
					stmt = conn.prepareStatement("select goodsID, goodsName from goods where brandID=?");
					stmt.setInt(1, brandID);
					rsTemp = stmt.executeQuery();
					while(rsTemp.next()){
						ArrayList<String> imgList = new ArrayList<String>();
						RecommendBean recommendBean = new RecommendBean();
						goodsID = rsTemp.getInt(1);
						recommendBean.setGoodsID(goodsID);
						goodsName = rsTemp.getString(2);
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
				}
			}
			else if(goodsNum>0){
				stmt = conn.prepareStatement("select goodsID from goods where upper(goodsName) like upper(?)");
				stmt.setString(1, '%'+value+'%');
				rs = stmt.executeQuery();
				while(rs.next()){
					goodsID = rs.getInt(1);
					stmt = conn.prepareStatement("select goodsID, goodsName from goods where goodsID=?");
					stmt.setInt(1, goodsID);
					rsTemp = stmt.executeQuery();
					while(rsTemp.next()){
						ArrayList<String> imgList = new ArrayList<String>();
						RecommendBean recommendBean = new RecommendBean();
						goodsID = rsTemp.getInt(1);
						recommendBean.setGoodsID(goodsID);
						goodsName = rsTemp.getString(2);
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
				}
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
