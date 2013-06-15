package dcd.academic.action;

import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.ShareDAO;
import dcd.academic.model.Gift;

/**
 * 
 * @author pelick
 * 在user.jsp用户主页展示用户分享记录列表
 *
 */
public class UserGiftAction extends ActionSupport {
	private String user;
	
	public ArrayList<Gift> list = new ArrayList<Gift>();
	
	@Override
	public String execute() throws Exception {
		ShareDAO dao = DAOfactory.getShareDAO();
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
