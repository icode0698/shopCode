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

import adminbean.Message;
import adminbean.User;
import api.DataLink;

public class DeleteSpu extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public DeleteSpu() {
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
		int spu = Integer.parseInt(request.getParameter("spu"));
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			stmt = conn.prepareStatement("delete from goodsValue where goodsID=?");
			stmt.setInt(1, spu);
			int i = stmt.executeUpdate();
			stmt = conn.prepareStatement("delete from img where goodsID=?");
			stmt.setInt(1, spu);
			int j = stmt.executeUpdate();
			stmt = conn.prepareStatement("delete from goods where goodsID=?");
			stmt.setInt(1, spu);
			int k = stmt.executeUpdate();
			if(i>0&&j>0&&k>0){
				json.put("status", "success");
				json.put("message", "商品信息删除成功");
			}
			else {
				json.put("status", "fail");
				json.put("message", "商品信息删除出现错误或者不存在该商品");
			}
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
