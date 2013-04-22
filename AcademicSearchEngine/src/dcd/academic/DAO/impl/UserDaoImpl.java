package dcd.academic.DAO.impl;

import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.UserDAO;
import dcd.academic.model.User;
import dcd.academic.mysql.dbpool.DBConnectionManage;

public class UserDaoImpl implements UserDAO {

	@Override
	public void addUser(User instance) {
		
		
	}
	
	@Override
	public User getUser(String name) {
		
		return null;
	}

	@Override
	public boolean existUser(String username, String pwd) {
		String query = "select * from academic_user where username=? and password=? ;";
		boolean is = false;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, pwd);
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
