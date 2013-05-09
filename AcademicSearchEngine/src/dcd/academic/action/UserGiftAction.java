package dcd.academic.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.ShareDAO;
import dcd.academic.model.Gift;

public class UserGiftAction extends ActionSupport {
	private String user;
	
	public ArrayList<Gift> list = new ArrayList<Gift>();
	
	@Override
	public String execute() throws Exception {
		DAOfactory factory = new DAOfactory();
		ShareDAO dao = factory.getShareDAO();
		list = dao.getGift(user);
		return SUCCESS;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<Gift> getList() {
		return list;
	}

	public void setList(ArrayList<Gift> list) {
		this.list = list;
	}
	
}
