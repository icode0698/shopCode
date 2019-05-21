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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import api.DataLink;

public class InsertSpu extends HttpServlet {
	public static int storageID = 31000000;
	public static int colorID = 32000000;
	public static int screenID = 33000000;
	/**
		 * Constructor of the object.
		 */
	public InsertSpu() {
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
		HttpSession session = request.getSession();
		int goodsID = Integer.parseInt(request.getParameter("spu"));
		int categoryID = Integer.parseInt(request.getParameter("categoryID"));
		int brandID = Integer.parseInt(request.getParameter("brandID"));
		int picID= Integer.parseInt(request.getParameter("picID"));
		JSONArray storageArray = new JSONArray(request.getParameter("storageList"));
		JSONArray colorArray = new JSONArray(request.getParameter("colorList"));
		JSONArray screenArray = new JSONArray(request.getParameter("screenList"));
//		System.out.println(storageArray);
    	session.setAttribute("goodsID", goodsID);
    	session.setAttribute("picID", picID);
		String goodsName = request.getParameter("name");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		conn = dataLink.linkData();
		try {
			stmt = conn.prepareStatement("insert into goods(goodsID,categoryID,brandID,goodsName) values(?,?,?,?)");
			stmt.setInt(1, goodsID);
			stmt.setInt(2, categoryID);
			stmt.setInt(3, brandID);
			stmt.setString(4, goodsName);
			int index = stmt.executeUpdate();
			if(index>0){
				ArrayList<Integer> storageIDList = new ArrayList<Integer>();
				ArrayList<Integer> colorIDList = new ArrayList<Integer>();
				ArrayList<Integer> screenIDList = new ArrayList<Integer>();
				int spID = 0;
				int sku = 0;
				stmt = conn.prepareStatement("select max(spID) from goodsvalue");
				ResultSet rs = stmt.executeQuery();
				while(rs.next()){
					spID = rs.getInt(1);
				}
				stmt = conn.prepareStatement("select max(sku) from price");
				rs = stmt.executeQuery();
				while(rs.next()){
					sku = rs.getInt(1);
				}
				//更新SPU属性
				spID = spID+1;
				for(int i=0;i<storageArray.length();i++){
					stmt = conn.prepareStatement("insert into goodsvalue(spID, goodsID, valueID, parameterID) values(?,?,?,?)");
					stmt.setInt(1, spID);
					stmt.setInt(2, goodsID);
					stmt.setInt(3, storageArray.getInt(i));
					stmt.setInt(4, storageID);
					stmt.executeUpdate();
					storageIDList.add(spID);
					spID++;
				}
				for(int i=0;i<colorArray.length();i++){
					stmt = conn.prepareStatement("insert into goodsvalue(spID, goodsID, valueID, parameterID) values(?,?,?,?)");
					stmt.setInt(1, spID);
					stmt.setInt(2, goodsID);
					stmt.setInt(3, colorArray.getInt(i));
					stmt.setInt(4, colorID);
					stmt.executeUpdate();
					colorIDList.add(spID);
					spID++;
				}
				for(int i=0;i<screenArray.length();i++){
					stmt = conn.prepareStatement("insert into goodsvalue(spID, goodsID, valueID, parameterID) values(?,?,?,?)");
					stmt.setInt(1, spID);
					stmt.setInt(2, goodsID);
					stmt.setInt(3, screenArray.getInt(i));
					stmt.setInt(4, screenID);
					stmt.executeUpdate();
					screenIDList.add(spID);
					spID++;
				}
				//更新SKU
				sku=sku+1;
				stmt = conn.prepareStatement("insert into price(sku,goodsID, spID1, spID2, spID3) values(?,?,?,?,?)");
				for(int i=0;i<storageIDList.size();i++){
					for(int j=0;j<colorIDList.size();j++){
						for(int k=0;k<screenIDList.size();k++){
							stmt.setInt(1, sku);
							stmt.setInt(2, goodsID);
							stmt.setInt(3, storageIDList.get(i));
							stmt.setInt(4, colorIDList.get(j));
							stmt.setInt(5, screenIDList.get(k));
							stmt.executeUpdate();
							sku++;
						}
					}
				}
				json.put("status", "success");
				json.put("message", "商品SPU添加成功，SKU已同步生成，需要添加价格和库存量");
			}
			else{
				json.put("status", "fail");
				json.put("message", "商品SPU添加出现错误");
			}
			out.write(json.toString());
			out.flush();
			out.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json.put("status", "fail");
			json.put("message", "商品SPU重复");
			System.out.println(json.toString());
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
