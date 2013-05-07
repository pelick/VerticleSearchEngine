package dcd.academic.DAO.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import dcd.academic.DAO.UserDAO;
import dcd.academic.model.PublicationModel;
import dcd.academic.model.ResearcherModel;
import dcd.academic.model.User;
import dcd.academic.mysql.dbpool.DBConnectionManage;
import dcd.academic.solrj.SolrjHelper;

public class UserDaoImpl implements UserDAO {

	@Override
	public void addUser(User instance) {
		String query = "insert into UserInfo(username, password, name, email, " +
				"weibo_url, github_url, homepage, interests) values(?, ?, ?, ?, ?, ?, ?, ?);";
		Connection con = null;
		PreparedStatement pst = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, instance.getUsername());
			pst.setString(2, instance.getPassword());
			pst.setString(3, instance.getName());
			pst.setString(4, instance.getEmail());
			pst.setString(5, instance.getWeibo_url());
			pst.setString(6, instance.getGithub_url());
			pst.setString(7, instance.getHomepage());
			pst.setString(8, instance.getInterests());
			pst.executeUpdate();		
		} catch (Exception e) {
			System.out.println("#######addUser Exception#######");
			e.printStackTrace();
		} finally {
			try {
				dbmanage.closeConnection(con);
			} catch (Exception e) {
			}
		}
	}
	
	

	@Override
	public ArrayList<ResearcherModel> getUserAuthor(String name) {
		ArrayList<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
		String query = "select * from UserAuthor where username=?;";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		
		SolrjHelper helper = new SolrjHelper(0);
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while (rs.next()) {
				String tmp = rs.getString("author").toString();
				ResearcherModel model = helper.getAuthorInfo(tmp);
				model.setDate(rs.getString("date").toString());
				authorlist.add(model);
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
		return authorlist;
	}



	@Override
	public ArrayList<PublicationModel> getUserPaper(String name) {
		ArrayList<PublicationModel> paperlist = new ArrayList<PublicationModel>();
		String query = "select * from UserPaper where username=?;";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DBConnectionManage dbmanage = DBConnectionManage.getInstance();
		
		SolrjHelper helper = new SolrjHelper(1);
		try {
			con = dbmanage.getFreeConnection();
			pst = (PreparedStatement) con.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while (rs.next()) {
				String tmp = rs.getString("title").toString();
				PublicationModel model = helper.getPaperInfo(tmp);
				model.setDate(rs.getString("date").toString());
				paperlist.add(model);
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
		return paperlist;
	}

	@Override
	public boolean existUser(String username, String pwd) {
		String query = "select * from UserInfo where username=? and password=? ;";
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
