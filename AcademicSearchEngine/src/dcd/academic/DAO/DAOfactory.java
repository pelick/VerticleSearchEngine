package dcd.academic.DAO;

import dcd.academic.DAO.impl.UserDaoImpl;
import dcd.academic.util.StdOutUtil;

public class DAOfactory {
	public UserDAO getUserDAO() {
		UserDAO dao = new UserDaoImpl();
		return dao;
	}
	
	public static void main(String[] args) {
		DAOfactory factory = new DAOfactory();
		UserDAO dao = factory.getUserDAO();
		StdOutUtil.out(dao.existUser("pelick", "123"));
	}
}


