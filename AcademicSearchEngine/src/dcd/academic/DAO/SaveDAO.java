package dcd.academic.DAO;

public interface SaveDAO {
	// 保存用户收藏学者、论文的信息
	public void saveAuthor(String username, String author, String date, String tag);
	public void savePaper(String username, String title, String tag, String date);
	
	// 检查是否存在收藏历史
	public boolean existAuthor(String username, String author);
	public boolean existPaper(String username, String title);
	
	// 保存和获取在discover.jsp的可视分析结果
	public void saveDiscover(String key, String type, String json);
	public String getDiscover(String key, String type);
	
	// 保存两个用户之间的相似度距离
	public void saveUserSimi(String user1, String user2, double simi);
}
