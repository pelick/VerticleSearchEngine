package dcd.academic.DAO;

public interface SaveDAO {
	public void saveAuthor(String username, String author, String date, String tag);
	public void savePaper(String username, String title, String tag, String date);
	
	public boolean existAuthor(String username, String author);
	public boolean existPaper(String username, String title);
	
	public void saveDiscover(String key, String type, String json);
	public String getDiscover(String key, String type);
	
	public void saveUserSimi(String user1, String user2, double simi);
}
