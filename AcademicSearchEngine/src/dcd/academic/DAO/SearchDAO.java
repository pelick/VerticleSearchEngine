package dcd.academic.DAO;

import java.util.ArrayList;

public interface SearchDAO {
	public void saveHistory(String type, String user, String sk, String date);
	
	public ArrayList<String> hotSearch();
	public ArrayList<String> newlySearch();
}
