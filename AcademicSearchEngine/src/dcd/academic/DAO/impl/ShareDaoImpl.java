package dcd.academic.DAO.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.ShareDAO;
import dcd.academic.model.Gift;
import dcd.academic.mysql.dbpool.DBConnectionManage;

public class ShareDaoImpl implements ShareDAO {
	@Override
	public void addGift(Gift gift) {
		String query = "insert into UserGift(user, type, content, url, " +
				"tag, date) values(?, ?, ?, ?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, gift.getUser());
			pst.setString(2, gift.getType());
			pst.setString(3, gift.getContent());
			pst.setString(4, gift.getUrl());
			pst.setString(5, gift.getTag());
			pst.setString(6, gift.getDate());
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######addGift Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
		
	}

	@Override
	public ArrayList<Gift> getGift(String username) {
		ArrayList<Gift> gifts = new ArrayList<Gift>();
		String query = "select * from UserGift where user=? order by date DESC;";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();
			while (rs.next()) {
				Gift tmp = new Gift();
				tmp.setContent(rs.getString("content").toString());
				tmp.setDate(rs.getString("date").toString());
				tmp.setUrl(rs.getString("url").toString());
				tmp.setUser(rs.getString("user").toString());
				tmp.setTag(rs.getString("tag").toString());
				tmp.setType(rs.getString("type").toString());
				gifts.add(tmp);
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
		return gifts;
	}
}
