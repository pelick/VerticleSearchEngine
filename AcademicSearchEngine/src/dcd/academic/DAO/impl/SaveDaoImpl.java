package dcd.academic.DAO.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.SaveDAO;
import dcd.academic.mysql.dbpool.DBConnectionManage;
import dcd.academic.recommend.BtwAuthor;
import dcd.academic.util.StdOutUtil;

public class SaveDaoImpl implements SaveDAO {

	@Override
	public void saveUserSimi(String user1, String user2, double simi) {
		String query = "insert into UserSimi(user1, user2, simi) values(?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
		
			pst.setString(1, user1);
			pst.setString(2, user2);
			pst.setDouble(3, simi);
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######saveSimi Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void saveAuthor(String username, String author, String date) {
		String query = "insert into UserAuthor(username, author, date) values(?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
		
			pst.setString(1, username);
			pst.setString(2, author);
			pst.setString(3, date);
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######saveAuthor Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void savePaper(String username, String title, String key, String date) {
		String query = "insert into UserPaper(username, title, date, sk) values(?, ?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
		
			pst.setString(1, username);
			pst.setString(2, title);
			pst.setString(3, date);
			pst.setString(4, key);
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######savePaper Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public boolean existAuthor(String username, String author) {
		String query = "select * from UserAuthor where username=? and author=? ;";
		boolean is = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, author);
			rs = pst.executeQuery();
			if (rs.next()) {
				is = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return is;
	}

	@Override
	public boolean existPaper(String username, String title) {
		String query = "select * from UserPaper where username=? and title=? ;";
		boolean is = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, title);
			rs = pst.executeQuery();
			if (rs.next()) {
				is = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return is;
	}

	@Override
	public void saveDiscover(String key, String type, String json) {
		String query = "insert into UserDiscover(sk, type, json) values(?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			
			pst.setString(1, key);
			pst.setString(2, type);
			pst.setString(3, json);
			StdOutUtil.out(pst.toString());
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######saveDiscover Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public String getDiscover(String key, String type) {
		String json = null;
		String query = "select * from UserDiscover where sk=? and type=?";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, key);
			pst.setString(2, type);
			StdOutUtil.out(pst.toString());
			rs = pst.executeQuery();
			if (rs.next()) {
				json = rs.getString("json");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	

}
