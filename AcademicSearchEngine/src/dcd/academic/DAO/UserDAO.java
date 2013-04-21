package dcd.academic.DAO;

import dcd.academic.model.User;

public interface UserDAO {
	
	public void addUser(User instance);
	public boolean existUser(String username, String pwd);
}
