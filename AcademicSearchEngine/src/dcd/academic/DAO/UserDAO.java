package dcd.academic.DAO;

import dcd.academic.model.User;

public interface UserDAO {
	
	public void addUser(User instance);
	public User getUser(String name);
	public boolean existUser(String username, String pwd);
}
