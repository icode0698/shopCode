package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataLink {
	String url = "jdbc:mysql://localhost/shop_online?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
	String userName = "root";
	String password = "123456";
	Connection conn = null;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public Connection linkData(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e1){
			System.out.println(e1.getMessage());
		}
		try{
			//conn = null;
			conn = DriverManager.getConnection(url,userName,password);
		}catch(SQLException e2){
			System.out.println(e2.getMessage());
		}
		if(conn==null){
			System.out.println("数据库连接失败");
			return null;
		}
		else
			System.out.println("数据库连接成功");
			return conn;
	}
}

