package dcd.academic.DAO;

import java.util.ArrayList;

import dcd.academic.model.PublicationModel;
import dcd.academic.model.ResearcherModel;
import dcd.academic.model.User;

public interface UserDAO {
	
	public void addUser(User instance);
	public User getUser(String username);
	public boolean existUser(String username, String pwd);
	
	public ArrayList<ResearcherModel> getUserAuthor(String name);
	public ArrayList<PublicationModel> getUserPaper(String name);
	
}
