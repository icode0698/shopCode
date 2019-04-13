package shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.DetailsBean;

public class Details extends HttpServlet {

	/**
		 * Constructor of the object.
		 */
	public Details() {
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
		//System.out.println(request.getParameter("goodsID"));		
		int goodsID = Integer.parseInt(request.getParameter("goodsID"));
		String brandName;
		DetailsBean detailsBean = new DetailsBean();
		ArrayList<DetailsBean> itemList = new ArrayList<DetailsBean>();
		//System.out.println("parstInt_goodsID:"+goodsID);
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try{
			ArrayList<String> imgList = new ArrayList<String>();
			ArrayList<String> storageList = new ArrayList<String>();
			ArrayList<String> colorList = new ArrayList<String>();
			ArrayList<String> screenList = new ArrayList<String>();
			stmt = conn.prepareStatement("select imgSrc from img where goodsID=?");
			stmt.setInt(1, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				imgList.add(rs.getString(1));
			}
			detailsBean.setImgList(imgList);
			stmt = conn.prepareStatement("select brandName from goods,brand "
					+ "where goods.brandID=brand.brandID and goods.goodsID=?");
			stmt.setInt(1, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				brandName = rs.getString(1);
//				System.out.println("brandName:"+brandName);
				detailsBean.setBrandName(brandName);
			}
			/* 
			 * 获取选中类别中产品的各项规格参数
			 * 1、获取存储参数
			 */
			stmt = conn.prepareStatement("select value from parameter,parametervalue,goodsvalue "
					+ "where parameter.parameterID=parametervalue.parameterID "
					+ "and goodsvalue.valueID=parametervalue.valueID and parameter.parameterName=? and goodsID=?");
			stmt.setString(1, "存储");
			stmt.setInt(2, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				storageList.add(rs.getString(1));
			}
			detailsBean.setStorageList(storageList);
			System.out.println("+++++++++++++++++++++++++++++++"+storageList);
			/*
			 * 2、获取颜色参数
			 */
			stmt.setString(1, "颜色");
			stmt.setInt(2, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				colorList.add(rs.getString(1));
			}
			detailsBean.setColorList(colorList);
			/*
			 * 3、获取尺寸参数
			 */
			stmt.setString(1, "尺寸");
			stmt.setInt(2, goodsID);
			rs = stmt.executeQuery();
			while(rs.next()){
				screenList.add(rs.getString(1));
			}
			detailsBean.setScreenList(screenList);
			itemList.add(detailsBean);
			json.put("status", "success");
			json.put("message", itemList);
			System.out.println("Details_itemList_item:"+json.get("status")+"&&"+json.get("message"));
			System.out.println("Details_itemList:"+json);
			out.write(json.toString());
			out.flush();
			out.close();
			rs.close();
			conn.close();
		}catch (SQLException e) {
			// TODO: handle exception
			e.getMessage();
			json.put("status", "failed");
			json.put("message", "服务器异常");
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
