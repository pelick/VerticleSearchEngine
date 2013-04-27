package dcd.academic.DAO.impl;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.SaveDAO;
import dcd.academic.mysql.dbpool.DBConnectionManage;

public class SaveDaoImlp implements SaveDAO {

	@Override
	public void saveAuthor(String username, String author) {
		String query = "insert into UserAuthor(username, author) values(?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
		
			pst.setString(1, username);
			pst.setString(2, author);
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
	public void savePaper(String username, String title) {
		String query = "insert into UserPaper(username, title) values(?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
		
			pst.setString(1, username);
			pst.setString(2, title);
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
	

}
