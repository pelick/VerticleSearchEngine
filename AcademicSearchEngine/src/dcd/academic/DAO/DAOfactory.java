package dcd.academic.DAO;

import dcd.academic.DAO.impl.SaveDaoImpl;
import dcd.academic.DAO.impl.SearchDaoImpl;
import dcd.academic.DAO.impl.ShareDaoImpl;
import dcd.academic.DAO.impl.UserDaoImpl;
import dcd.academic.util.StdOutUtil;

public class DAOfactory {
	public static UserDAO getUserDAO() {
		UserDAO dao = new UserDaoImpl();
		return dao;
	}
	
	public static SaveDAO getSaveDAO() {
		SaveDAO dao = new SaveDaoImpl();
		return dao;
	}
	
	public static ShareDAO getShareDAO() {
		ShareDAO dao = new ShareDaoImpl();
		return dao;
	}
	
	public static SearchDAO getSearchDAO() {
		SearchDAO dao = new SearchDaoImpl();
		return dao;
	}
	
	public static void main(String[] args) {
		DAOfactory factory = new DAOfactory();
		UserDAO dao = factory.getUserDAO();
		StdOutUtil.out(dao.existUser("pelick", "123"));
	}
}


