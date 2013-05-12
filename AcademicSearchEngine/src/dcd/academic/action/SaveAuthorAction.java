package dcd.academic.action;

import java.util.Calendar;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;

public class SaveAuthorAction extends ActionSupport {
	
	private String user;
	private String author;
	
	public int type;
	
	@Override
	public String execute() throws Exception {
		SaveDAO dao = DAOfactory.getSaveDAO();
		if (!dao.existAuthor(user, author)) {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			dao.saveAuthor(user, author, date.toLocaleString());
			type = 1;
		} else {
			type = 0;
		}
		return SUCCESS;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
