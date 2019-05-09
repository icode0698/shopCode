package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkuStock {
	int sku = 0;
	int stock = 0;
	public SkuStock(int sku){
		this.sku = sku;
	}
	public int getStock(){
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		ResultSet rs = null;
		try {
			PreparedStatement stmt = conn.prepareStatement("select stock from price where sku=?");
			stmt.setInt(1, sku);
			rs = stmt.executeQuery();
			while(rs.next()){
				stock = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return stock;
	}
	public int reduceStock(int num){
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		int index = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement("update price set stock=stock-? where sku=?");
			stmt.setInt(1, num);
			stmt.setInt(2, sku);
			index = stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("stockIndex:"+index);
		if(index>0){
			return 1;
		}
		else{
			return 0;
		}
	}
	public int increaseStock(int num){
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		int index = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement("update price set stock=stock+? where sku=?");
			stmt.setInt(1, num);
			stmt.setInt(2, sku);
			index = stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("stockIndex:"+index);
		if(index>0){
			return 1;
		}
		else{
			return 0;
		}
	}
}
