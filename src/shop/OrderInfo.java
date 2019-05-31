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
import api.SkuToSpu;
import bean.RecommendBean;

public class OrderInfo extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public OrderInfo() {
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
		ResultSet rs = null;
		HttpSession session = request.getSession();
		String user = "";
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(session.getAttribute("user")==null){
			json.put("status", "fail");
			json.put("message", "用户未登录或登录会话超时");
			
		}
		else {
			user = session.getAttribute("user").toString();
			try {
				stmt = conn.prepareStatement("select count(*) from shop where id=? and user=?");
				stmt.setString(1, id);
				stmt.setString(2, user);
				rs = stmt.executeQuery();
				rs.next();
				if(rs.getInt(1)==0){
					json.put("status", "fail");
					json.put("message", "没有找到相关订单");
				}
				else{
					stmt = conn.prepareStatement("select sku, goodsName, storage, color, screen from shop where id=? and user=?");
					stmt.setString(1, id);
					stmt.setString(2, user);
					rs = stmt.executeQuery();
					while(rs.next()){
						json.put("status", "success");
						json.put("sku", rs.getInt(1));
						SkuToSpu skuToSpu = new SkuToSpu(rs.getInt(1));
						int spu = skuToSpu.getSpu();
						json.put("spu", spu);
						json.put("goodsName", rs.getString(2));
						json.put("storage", rs.getString(3));
						json.put("color", rs.getString(4));
						json.put("screen", rs.getString(5));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
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
