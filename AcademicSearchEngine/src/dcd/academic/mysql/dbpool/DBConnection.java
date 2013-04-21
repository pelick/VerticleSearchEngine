package dcd.academic.mysql.dbpool;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class DBConnection { // ������ݿ�������
	private String url;
	private String user;
	private String pwd;
	private String forName;
	private Connection connection;
	private int key;
	
	public DBConnection(String url, String user, String pwd, String forName, int key) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.forName = forName;
		this.key = key;
		connection = getConnectionInstance();
	}
	
	public DBConnection() {}
	
	// ����connction����
	public Connection getConnectionInstance() {
		try {
			Class.forName(forName).newInstance();
			connection = (Connection) DriverManager.getConnection(url, user, pwd);
			
		} catch (Exception e) {}
		return connection;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public String getForName() {
		return forName;
	}
	
	public void setForName(String forName) {
		this.forName = forName;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String urlString) {
		this.url = url;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
