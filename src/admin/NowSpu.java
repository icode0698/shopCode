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

import adminbean.Brand;
import adminbean.Category;
import adminbean.Color;
import adminbean.Screen;
import adminbean.Storage;
import api.DataLink;

public class NowSpu extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public NowSpu() {
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
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		int spu = 0;
		ArrayList<Brand> brandList = new ArrayList<Brand>();
		ArrayList<Category> categoryList = new ArrayList<Category>();
		ArrayList<Storage> storageList = new ArrayList<Storage>();
		ArrayList<Color> colorList = new ArrayList<Color>();
		ArrayList<Screen> screenList = new ArrayList<Screen>();
		try {
			stmt = conn.prepareStatement("select max(goodsID) from goods");
			rs = stmt.executeQuery();
			while(rs.next()){
				spu = rs.getInt(1);
			}
			json.put("status", "success");
			json.put("spu", spu);
			stmt = conn.prepareStatement("select * from brand");
			rs = stmt.executeQuery();
			while(rs.next()){
				Brand brand = new Brand();
				brand.setBrandID(rs.getInt(1));
				brand.setBrandName(rs.getString(2));
				brand.setInsertTime(rs.getString(3));
				brandList.add(brand);
			}
			json.put("brandList", brandList);
			
			stmt = conn.prepareStatement("select * from category");
			rs = stmt.executeQuery();
			while(rs.next()){
				Category category = new Category();
				category.setCategoryID(rs.getInt(1));
				category.setCategoryName(rs.getString(2));
				category.setInsertTime(rs.getString(3));
				categoryList.add(category);
			}
			json.put("categoryList", categoryList);
			
			stmt = conn.prepareStatement("select valueID,value from parameter,parametervalue "
					+ "where parameter.parameterID = parametervalue.parameterID and parameterName=?");
			stmt.setString(1, "存储");
			rs = stmt.executeQuery();
			while(rs.next()){
				Storage storage = new Storage();
				storage.setId(rs.getInt(1));
				storage.setValue(rs.getString(2));
				storageList.add(storage);
			}
			json.put("storageList", storageList);
			stmt = conn.prepareStatement("select valueID,value from parameter,parametervalue "
					+ "where parameter.parameterID = parametervalue.parameterID and parameterName=?");
			stmt.setString(1, "颜色");
			rs = stmt.executeQuery();
			while(rs.next()){
				Color color = new Color();
				color.setId(rs.getInt(1));
				color.setValue(rs.getString(2));
				colorList.add(color);
			}
			json.put("colorList", colorList);
			stmt = conn.prepareStatement("select valueID,value from parameter,parametervalue "
					+ "where parameter.parameterID = parametervalue.parameterID and parameterName=?");
			stmt.setString(1, "尺寸");
			rs = stmt.executeQuery();
			while(rs.next()){
				Screen screen = new Screen();
				screen.setId(rs.getInt(1));
				screen.setValue(rs.getString(2));
				screenList.add(screen);
			}
			json.put("screenList", screenList);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			stmt.close();
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
