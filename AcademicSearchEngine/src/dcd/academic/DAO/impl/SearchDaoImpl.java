package dcd.academic.DAO.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.SearchDAO;
import dcd.academic.model.Gift;
import dcd.academic.mysql.dbpool.DBConnectionManage;

public class SearchDaoImpl implements SearchDAO {

	@Override
	public void saveHistory(String type, String user, String sk, String date) {
		String query = "insert into SearchHistory(type, user, sk, date) " +
				"values(?, ?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, type);
			pst.setString(2, user);
			pst.setString(3, sk);
			pst.setString(4, date);
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######saveHistory Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public ArrayList<String> hotSearch() {
		ArrayList<String> hot = new ArrayList<String>();
		String query = "SELECT sk, count(sk) as count FROM SearchHistory group by sk order by count DESC limit 10;";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				hot.add("("+rs.getString("count").toString()+") "+rs.getString("sk").toString());
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
		return hot;
	}

	@Override
	public ArrayList<String> newlySearch() {
		ArrayList<String> newly = new ArrayList<String>();
		String query = "select sk from SearchHistory order by date DESC limit 10;";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				newly.add(rs.getString("sk").toString());
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
		return newly;
	}
	
	
	
}
