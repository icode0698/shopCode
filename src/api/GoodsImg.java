package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsImg {
	int spu;
	ArrayList<String> imgList = new ArrayList<String>();
	public GoodsImg(int spu){
		this.spu = spu;
	}
	public ArrayList<String> getImg(){
		DataLink dataLink =  new DataLink();
		Connection conn = dataLink.linkData();
		ResultSet rs;
		try{
			PreparedStatement stmt = conn.prepareStatement("select imgSrc from img where goodsID=?");
			stmt.setInt(1, spu);
			rs = stmt.executeQuery();
			while(rs.next()){
				imgList.add(rs.getString(1));
			}
			//System.out.println("GoodsImg:"+imgList);
			rs.close();
			conn.close();
			stmt.close();
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return imgList;
	}
}
