package dcd.academic.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.model.PublicationModel;

/**
 * 
 * @author pelick
 * 在Home.jsp内，给用户推荐论文时使用。具体操作在DAO的sql操作内。
 *
 */
public class RecommendAction extends ActionSupport {
	
	private String user;
	
	public ArrayList<PublicationModel> list = new ArrayList<PublicationModel>();
	
	@Override
	public String execute() throws Exception {
		list = DAOfactory.getUserDAO().recommendPaper(user);
		return SUCCESS;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<PublicationModel> getList() {
		return list;
	}

	public void setList(ArrayList<PublicationModel> list) {
		this.list = list;
	}
	
	

}
