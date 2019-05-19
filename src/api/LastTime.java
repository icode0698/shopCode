package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

public class LastTime {
	HttpSession session;
	public LastTime(HttpSession session){
		this.session = session;
	}
	public void update() {
		String user = session.getAttribute("user").toString();
		System.out.println("SessionListenerDestroyed:"+user);
		DataLink dataLink = new DataLink();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dataLink.linkData();
			stmt = conn.prepareStatement("update user set lastTime=currentTime where user=?");
			stmt.setString(1, user);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
