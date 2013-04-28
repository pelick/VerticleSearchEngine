package dcd.academic.DAO;

import java.util.ArrayList;

import dcd.academic.model.User;

public interface UserDAO {
	
	public void addUser(User instance);
	public ArrayList<String> getUserAuthor(String name);
	public ArrayList<String> getUserPaper(String name);
	public boolean existUser(String username, String pwd);
}
