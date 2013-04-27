package dcd.academic.DAO;

public interface SaveDAO {
	public void saveAuthor(String username, String author);
	public void savePaper(String username, String title);
	
	public boolean existAuthor(String username, String author);
	public boolean existPaper(String username, String title);
}
