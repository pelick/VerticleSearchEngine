package dcd.academic.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.UserDAO;
import dcd.academic.DAO.impl.UserDaoImpl;
import dcd.academic.model.PublicationModel;
/**
 * 
 * @author pelick
 * 在user.jsp用户主页展示用户收藏的论文列表
 *
 */
public class UserPaperAction extends ActionSupport {
	private String user;
	
	public ArrayList<PublicationModel> list = new ArrayList<PublicationModel>();
	
	@Override
	public String execute() throws Exception {
		UserDAO dao = DAOfactory.getUserDAO();
		list = dao.getUserPaper(user);
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
