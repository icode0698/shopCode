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
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import api.DataLink;
import api.SkuStock;
import api.SpIDToValue;
import bean.CommentBean;

public class ShowComment extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public ShowComment() {
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
		int spu = Integer.parseInt(request.getParameter("spu"));
		SpIDToValue spIDToValue = new SpIDToValue();
		ArrayList<CommentBean> commentList = new ArrayList<CommentBean>();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			stmt = conn.prepareStatement("select * from comment where spu=? order by createTime desc");
			stmt.setInt(1, spu);
			rs = stmt.executeQuery();
			while(rs.next()){
				CommentBean commentBean = new CommentBean();
				commentBean.setId(rs.getInt(1));
				commentBean.setUser(rs.getString(2));
				stmtIn = conn.prepareStatement("select headPic from user where user=?");
				stmtIn.setString(1, rs.getString(2));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					commentBean.setHeadPic(rsIn.getString(1));
				}
				commentBean.setSku(rs.getInt(3));
				stmtIn = conn.prepareStatement("select spID1,spID2,spID3 from price where sku=?");
				stmtIn.setInt(1, rs.getInt(3));
				rsIn = stmtIn.executeQuery();
				while(rsIn.next()){
					commentBean.setStorage(spIDToValue.getSpValue(rsIn.getInt(1)));
					commentBean.setColor(spIDToValue.getSpValue(rsIn.getInt(2)));
					commentBean.setScreen(spIDToValue.getSpValue(rsIn.getInt(3)));
				}
				commentBean.setSpu(rs.getInt(4));
				commentBean.setComment(rs.getString(5));
				commentBean.setRateValue(rs.getFloat(6));
				commentBean.setCreateTime(rs.getString(7));
				commentList.add(commentBean);
			}
			json.put("status", "success");
			json.put("message", commentList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", "error");
			json.put("message", "商品评价加载出现未知错误");
		}
		out.write(json.toString());
		out.flush();
		out.close();
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
