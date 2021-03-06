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

import adminbean.Spu;
import api.DataLink;

public class SelectSpu extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public SelectSpu() {
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
		ArrayList<Spu> spuList = new ArrayList<Spu>();
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from goods");
			PreparedStatement temp;
			ResultSet rs = stmt.executeQuery();
			ResultSet tempRs;
			while(rs.next()){
				Spu spu = new Spu();
				spu.setGoodsID(rs.getInt(1));
				temp = conn.prepareStatement("select categoryName from category where categoryID=?");
				temp.setInt(1, rs.getInt(2));
				tempRs = temp.executeQuery();
				while(tempRs.next()){
					spu.setCategoryName(tempRs.getString(1));
				}
				temp = conn.prepareStatement("select brandName from brand where brandID=?");
				temp.setInt(1, rs.getInt(3));
				tempRs = temp.executeQuery();
				while(tempRs.next()){
					spu.setBrandName(tempRs.getString(1));
				}
				spu.setGoodsName(rs.getString(4));
				spu.setInsertTime(rs.getString(5));
				spuList.add(spu);
			}
			json.put("status", "success");
			json.put("message", spuList);
			out.write(json.toString());
			out.flush();
			out.close();
			stmt.close();
			rs.close();
			conn.close();
			
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
