package dcd.academic.action;

import com.opensymphony.xwork2.ActionSupport;

import dcd.academic.DAO.UserDAO;
import dcd.academic.DAO.impl.UserDaoImpl;
import dcd.academic.model.User;

/**
 * 
 * @author pelick
 * 注册用户时触发，传给DAO层存储数据
 *
 */
public class RegisterAction extends ActionSupport {
	private String username;
	private String password;
	private String name;
	private String email;
	private String weibo;
	private String github;
	private String homepage;
	private String interests;
	
	@Override
	public String execute() throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setName(name);
		user.setWeibo_url(weibo);
		user.setGithub_url(github);
		user.setHomepage(homepage);
		user.setInterests(interests);
		UserDAO dao = new UserDaoImpl();
		dao.addUser(user);
		
		return SUCCESS;
	}

}
