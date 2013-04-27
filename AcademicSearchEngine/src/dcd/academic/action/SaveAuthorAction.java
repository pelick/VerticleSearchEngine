package dcd.academic.action;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.util.StdOutUtil;

public class SaveAuthorAction extends ActionSupport {
	
	private String user;
	private String author;
	
	public int type;
	
	@Override
	public String execute() throws Exception {
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		if (!dao.existAuthor(user, author)) {
			dao.saveAuthor(user, author);
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
