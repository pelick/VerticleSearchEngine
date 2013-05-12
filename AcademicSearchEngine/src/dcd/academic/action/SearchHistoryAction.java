package dcd.academic.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SearchDAO;

public class SearchHistoryAction extends ActionSupport {
	
	private String user;
	private String type;
	private String sk;
	
	public ArrayList<String> hotlist = new ArrayList<String>();
	public ArrayList<String> newlylist = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		if (user == null) {
			user = "";
		}
		
		SearchDAO dao = DAOfactory.getSearchDAO();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		if (sk.length() > 2 && sk != null) {
			dao.saveHistory(type, user, sk.toLowerCase(), date.toLocaleString());
		}
		
		hotlist = dao.hotSearch();
		newlylist = dao.newlySearch();
		
		return SUCCESS;
	}

	public ArrayList<String> getHotlist() {
		return hotlist;
	}

	public void setHotlist(ArrayList<String> hotlist) {
		this.hotlist = hotlist;
	}

	public ArrayList<String> getNewlylist() {
		return newlylist;
	}

	public void setNewlylist(ArrayList<String> newlylist) {
		this.newlylist = newlylist;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}
	
	
}
