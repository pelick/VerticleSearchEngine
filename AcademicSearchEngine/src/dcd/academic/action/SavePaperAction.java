package dcd.academic.action;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.util.StdOutUtil;

public class SavePaperAction extends ActionSupport {
	
	private String user;
	private String title;
	
	public int type;
	
	@Override
	public String execute() throws Exception {
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		if (!dao.existPaper(user, title)) {
			dao.savePaper(user, title);
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

	public String getTitler() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
