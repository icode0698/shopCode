package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import api.DataLink;
import api.SkuToSpu;

public class Comment extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Comment() {
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
		float rateValue = Float.parseFloat(request.getParameter("rateValue"));
		String commentContent = request.getParameter("commentContent");
		int sku = Integer.parseInt(request.getParameter("sku"));
		int spu = Integer.parseInt(request.getParameter("spu"));
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if(session.getAttribute("user")==null){
			json.put("status", "fail");
			json.put("message", "用户未登录或登录会话超时");
		}
		else {
			user = session.getAttribute("user").toString();
			try {
				stmt = conn.prepareStatement("insert into comment(user, sku, spu, comment, rateValue) values(?, ?, ?, ?, ?)");
				stmt.setString(1, user);
				stmt.setInt(2, sku);
				stmt.setInt(3, spu);
				stmt.setString(4, commentContent);
				stmt.setFloat(5, rateValue);
				int index = stmt.executeUpdate();
				if(index>0){
					json.put("status", "success");
					json.put("message", "商品评价成功");
				}
				else{
					json.put("status", "fail");
					json.put("message", "商品评价出现错误");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				json.put("status", "fail");
				json.put("message", "商品评价出现未知错误，请联系管理员");
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
