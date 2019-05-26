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

import adminbean.Sku;
import api.DataLink;
import api.SpIDToValue;

public class SelectSku extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public SelectSku() {
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
		String category = request.getParameter("category");
		String spuString = request.getParameter("spu");
		String skuString = request.getParameter("sku");
		ArrayList<Sku> skuList = new ArrayList<Sku>();
		PreparedStatement stmt = null;
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			if(category==null||"".equals(category)){
				if(spuString==null||"".equals(spuString)){
					if(skuString==null|"".equals(skuString)){
						stmt = conn.prepareStatement("select * from price");
						PreparedStatement tempStmt;
						ResultSet rs = stmt.executeQuery();
						ResultSet tempRs;
						while(rs.next()){
							Sku sku = new Sku();
							sku.setSKU(rs.getInt(1));
							sku.setGoodsID(rs.getInt(2));
							sku.setPrice(rs.getFloat(6));
							sku.setStock(rs.getInt(7));
							tempStmt = conn.prepareStatement("select goodsName,categoryID,brandID from goods where goodsID=?");
							tempStmt.setInt(1, rs.getInt(2));
							tempRs = tempStmt.executeQuery();
							while(tempRs.next()){
								sku.setGoodsName(tempRs.getString(1));
								tempStmt = conn.prepareStatement("select categoryName from category where categoryID=?");
								tempStmt.setInt(1, tempRs.getInt(2));
								ResultSet rsIn = tempStmt.executeQuery();
								while(rsIn.next()){
									sku.setCategoryName(rsIn.getString(1));
								}
								tempStmt = conn.prepareStatement("select brandName from brand where brandID=?");
								tempStmt.setInt(1, tempRs.getInt(3));
								rsIn = tempStmt.executeQuery();
								while(rsIn.next()){
									sku.setBrandName(rsIn.getString(1));
								}
							}
							SpIDToValue spIDToValue = new SpIDToValue();
							sku.setStorage(spIDToValue.getSpValue(rs.getInt(3)));
							sku.setColor(spIDToValue.getSpValue(rs.getInt(4)));
							sku.setScreen(spIDToValue.getSpValue(rs.getInt(5)));
							skuList.add(sku);
						}
						json.put("status", "success");
						json.put("message", skuList);
					}
					else{
						int skuID = Integer.parseInt(skuString);
						stmt = conn.prepareStatement("select * from price where sku=?");
						stmt.setInt(1, skuID);
						PreparedStatement tempStmt;
						ResultSet rs = stmt.executeQuery();
						ResultSet tempRs;
						while(rs.next()){
							Sku sku = new Sku();
							sku.setSKU(rs.getInt(1));
							sku.setGoodsID(rs.getInt(2));
							sku.setPrice(rs.getFloat(6));
							sku.setStock(rs.getInt(7));
							tempStmt = conn.prepareStatement("select goodsName,categoryID,brandID from goods where goodsID=?");
							tempStmt.setInt(1, rs.getInt(2));
							tempRs = tempStmt.executeQuery();
							while(tempRs.next()){
								sku.setGoodsName(tempRs.getString(1));
								tempStmt = conn.prepareStatement("select categoryName from category where categoryID=?");
								tempStmt.setInt(1, tempRs.getInt(2));
								ResultSet rsIn = tempStmt.executeQuery();
								while(rsIn.next()){
									sku.setCategoryName(rsIn.getString(1));
								}
								tempStmt = conn.prepareStatement("select brandName from brand where brandID=?");
								tempStmt.setInt(1, tempRs.getInt(3));
								rsIn = tempStmt.executeQuery();
								while(rsIn.next()){
									sku.setBrandName(rsIn.getString(1));
								}
							}
							SpIDToValue spIDToValue = new SpIDToValue();
							sku.setStorage(spIDToValue.getSpValue(rs.getInt(3)));
							sku.setColor(spIDToValue.getSpValue(rs.getInt(4)));
							sku.setScreen(spIDToValue.getSpValue(rs.getInt(5)));
							skuList.add(sku);
						}
						json.put("status", "success");
						json.put("message", skuList);
					}
				}
				else{
					int spuID = Integer.parseInt(spuString);
					stmt = conn.prepareStatement("select * from price where goodsID=?");
					stmt.setInt(1, spuID);
					PreparedStatement tempStmt;
					ResultSet rs = stmt.executeQuery();
					ResultSet tempRs;
					while(rs.next()){
						Sku sku = new Sku();
						sku.setSKU(rs.getInt(1));
						sku.setGoodsID(rs.getInt(2));
						sku.setPrice(rs.getFloat(6));
						sku.setStock(rs.getInt(7));
						tempStmt = conn.prepareStatement("select goodsName,categoryID,brandID from goods where goodsID=?");
						tempStmt.setInt(1, rs.getInt(2));
						tempRs = tempStmt.executeQuery();
						while(tempRs.next()){
							sku.setGoodsName(tempRs.getString(1));
							tempStmt = conn.prepareStatement("select categoryName from category where categoryID=?");
							tempStmt.setInt(1, tempRs.getInt(2));
							ResultSet rsIn = tempStmt.executeQuery();
							while(rsIn.next()){
								sku.setCategoryName(rsIn.getString(1));
							}
							tempStmt = conn.prepareStatement("select brandName from brand where brandID=?");
							tempStmt.setInt(1, tempRs.getInt(3));
							rsIn = tempStmt.executeQuery();
							while(rsIn.next()){
								sku.setBrandName(rsIn.getString(1));
							}
						}
						SpIDToValue spIDToValue = new SpIDToValue();
						sku.setStorage(spIDToValue.getSpValue(rs.getInt(3)));
						sku.setColor(spIDToValue.getSpValue(rs.getInt(4)));
						sku.setScreen(spIDToValue.getSpValue(rs.getInt(5)));
						skuList.add(sku);
					}
					json.put("status", "success");
					json.put("message", skuList);
				}
			}
			else{
				int categoryID = 0;
				int spuID = 0;
				stmt = conn.prepareStatement("select categoryID from category where categoryName=?");
				stmt.setString(1, category);
				ResultSet rsOut = stmt.executeQuery();
				while(rsOut.next()){
					categoryID = rsOut.getInt(1);
				}
				stmt = conn.prepareStatement("select goodsID from goods where categoryID=?");
				stmt.setInt(1, categoryID);
				rsOut = stmt.executeQuery();
				while(rsOut.next()){
					spuID = rsOut.getInt(1);
					stmt = conn.prepareStatement("select * from price where goodsID=?");
					stmt.setInt(1, spuID);
					PreparedStatement tempStmt;
					ResultSet rs = stmt.executeQuery();
					ResultSet tempRs;
					while(rs.next()){
						Sku sku = new Sku();
						sku.setSKU(rs.getInt(1));
						sku.setGoodsID(rs.getInt(2));
						sku.setPrice(rs.getFloat(6));
						sku.setStock(rs.getInt(7));
						tempStmt = conn.prepareStatement("select goodsName,categoryID,brandID from goods where goodsID=?");
						tempStmt.setInt(1, rs.getInt(2));
						tempRs = tempStmt.executeQuery();
						while(tempRs.next()){
							sku.setGoodsName(tempRs.getString(1));
							tempStmt = conn.prepareStatement("select categoryName from category where categoryID=?");
							tempStmt.setInt(1, tempRs.getInt(2));
							ResultSet rsIn = tempStmt.executeQuery();
							while(rsIn.next()){
								sku.setCategoryName(rsIn.getString(1));
							}
							tempStmt = conn.prepareStatement("select brandName from brand where brandID=?");
							tempStmt.setInt(1, tempRs.getInt(3));
							rsIn = tempStmt.executeQuery();
							while(rsIn.next()){
								sku.setBrandName(rsIn.getString(1));
							}
						}
						SpIDToValue spIDToValue = new SpIDToValue();
						sku.setStorage(spIDToValue.getSpValue(rs.getInt(3)));
						sku.setColor(spIDToValue.getSpValue(rs.getInt(4)));
						sku.setScreen(spIDToValue.getSpValue(rs.getInt(5)));
						skuList.add(sku);
					}
				}
				json.put("status", "success");
				json.put("message", skuList);
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
