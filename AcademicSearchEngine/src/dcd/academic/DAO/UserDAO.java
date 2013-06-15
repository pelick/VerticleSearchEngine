package dcd.academic.DAO;

import java.util.ArrayList;

import dcd.academic.model.PublicationModel;
import dcd.academic.model.ResearcherModel;
import dcd.academic.model.User;

public interface UserDAO {
	
	// 和用户相关的数据库表处理
	public void addUser(User instance);
	public User getUser(String username);
	public boolean existUser(String username, String pwd);
	
	// 和用户收藏的学者列表、论文列表以及收藏标签列表
	public ArrayList<ResearcherModel> getUserAuthor(String name);
	public ArrayList<PublicationModel> getUserPaper(String name);
	public ArrayList<String> getUserKeys(String name);
	
	public ArrayList<PublicationModel> recommendPaper(String name);
}
