package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkuToSpu {
	int sku;
	int spu;
	public SkuToSpu(int sku){
		this.sku = sku;
	}
	public int getSpu() {
		DataLink dataLink =  new DataLink();
		Connection conn = dataLink.linkData();
		ResultSet rs;
		try{
			PreparedStatement stmt = conn.prepareStatement("select goodsID from "
					+ "price where sku=?");
			stmt.setInt(1, sku);
			rs = stmt.executeQuery();
			while(rs.next()){
				spu = rs.getInt(1);
			}
			rs.close();
			conn.close();
			stmt.close();
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return spu;
	}
}
