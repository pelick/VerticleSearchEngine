package dcd.academic.mysql.dbpool;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;



import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class DBConnectionManage { // ������ݿ����ӳ���
	
	// ����������ݿ����ӳصĻ����
	private Hashtable<Integer, DBConnection> connectionpool;
	
	// �������������
	private int maxconnection;
	
	// ��¼��ǰ��������
	private int currentconn;
	
	public DBConnectionManage() {
		init();
	}
	
	public static DBConnectionManage getInstance() {
		return new DBConnectionManage();
	}
	
	public void init() {
		connectionpool = new Hashtable<Integer, DBConnection>();
		maxconnection = 20;
		currentconn = 0;
		initConnection();
	}
	
	// ��connectionpool�������һ����������ݿ����ӣ��Թ�ʹ��
	public void initConnection() {
		DBConnection addconn = new DBConnection();
		for (int i=1; i <= maxconnection/2; i++) {
			addconn.setKey(i);
			connectionpool.put(i, addconn);
		}
	}
	
	// ��ȡDBConnection����
	synchronized public DBConnection getConnection() {
		DBConnection dbconn = null;
		
		try {
			// ������ϢҪ�޸�
			String url = "jdbc:mysql://localhost:3307/academic?user=root"; // ������,������ݿ�opac
			String user = "root";
			String pwd = "";
			String forName = "com.mysql.jdbc.Driver"; // �ĳ�MySQL��
			dbconn = new DBConnection(url, user, pwd, forName, 0);
			currentconn ++;
			// System.out.println("���Connection, ��װDBConnection.");
			
		} catch (Exception e) {
			System.out.println("���Connection����.");
		}
		return dbconn;
	}
	
	
	// ͨ��key���Connection����
	public Connection getFreeConnection() {
		Connection conn = null;
		DBConnection model =null;
		Object key;
		boolean foundconnection = false;
		for (int i=0; i < connectionpool.size(); i ++) {
			// ����ݿ����ӳ��н�key��ȡ��
			Enumeration<Integer> e = connectionpool.keys();
			
			while (e.hasMoreElements()) {
				key = e.nextElement();
				// ��keyȡ����ݿ�����
				model = (DBConnection) connectionpool.get(key); 
				conn = model.getConnection();
				// �ӻ�������Ƴ�Connection����
				connectionpool.remove(key);
				// ���������ݿ������������٣����Զ�����������
				// ���hashtable��put�����쳣
				try { 
					while (connectionpool.size() < maxconnection/2 ) {
					connectionpool.put(getMaxkey(connectionpool) + 1, getConnection());
				}
				} catch (Exception ex) {
					// TODO: handle exception
					System.out.println(ex + "in put connections into pool.");
				}
				
				foundconnection = true;
				// System.out.println("�ӻ�����гɹ���ȡConnection");
				break;
			}
		}
		// ���ӻ�����ж�ȡʧ�ܣ����������һ��Connection����
		if (!foundconnection) {
			// ��ջ�����е�����
			release();
			// ������ɻ����
			initConnection();
			model = getConnection();
			conn = model.getConnection();
		}
		return conn;
	}


	// ��ȡ����������key,���Ա���
	private int getMaxkey(Hashtable<Integer, DBConnection> cp) {
		int maxkey = 0;
		int tmpkey = 0;
		
		Enumeration<Integer> e = connectionpool.keys();
		while (e.hasMoreElements()) {
			try {
				tmpkey = Integer.parseInt(e.nextElement().toString());
			} catch (Exception ex) {
				tmpkey = 0;
			}
			
			if (maxkey < tmpkey) {
				maxkey = tmpkey;
			}
		}
		return maxkey;
	}
	
	// �ر���ݿ�����
	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				System.err.println("�ر���ݿ�����ʱ�������" + ex.getMessage());
			}
		}
	}
	
	// �����ݿ�Ļ�������
	private void release() {
		
		Enumeration<DBConnection> e = connectionpool.elements();
		DBConnection model = null;
		Connection modelconn = null;
		
		while (e.hasMoreElements()) {
			model = (DBConnection) e.nextElement();
			modelconn = model.getConnection();
			closeConnection(modelconn);
		}
		
	}
	
	// �������Բ���ʹ�����dbpool
	public static void main(String args[]) {
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		Connection conn = dbmanage.getFreeConnection();
		ResultSet rs = null;
		String query = "select * from member"; // 
		java.sql.Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(query);
			
			if (rs.next()) {
				
				System.out.println(rs.getString("memberusername"));
				
			} else {
				
				System.out.println("The selection is over.");
				
			}
	
		} catch (Exception ex) {
			System.out.println("it`s exp" + ex);
		} finally 
		{
			try {
				rs.close();
				stmt.close();
				dbmanage.closeConnection(conn);
			} catch (Exception ex) {
				System.err.println("it`s exp" + ex);
			}
		}
	}
}
