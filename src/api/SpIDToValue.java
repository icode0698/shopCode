package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SpIDToValue {
	String spValue;
	public String getSpValue(int spID){
		DataLink dataLink = new DataLink();
		Connection conn = dataLink.linkData();
		try {
			PreparedStatement stmt = conn.prepareStatement("select value from goodsvalue,parametervalue where goodsvalue.valueID=parametervalue.valueID and spID=?");
			stmt.setInt(1, spID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				spValue = rs.getString(1);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return spValue;
	}
	
}
