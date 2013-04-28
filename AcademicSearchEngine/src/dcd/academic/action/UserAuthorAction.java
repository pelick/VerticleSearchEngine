package dcd.academic.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.UserDAO;
import dcd.academic.DAO.impl.UserDaoImpl;

public class UserAuthorAction extends ActionSupport {
	private String user;
	
	public ArrayList<String> list = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		UserDAO dao = new UserDaoImpl();
		list = dao.getUserAuthor(user);
		return SUCCESS;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
	
}
