package dcd.academic.DAO;

import java.util.ArrayList;

public interface SearchDAO {
	// 记录每次在index.jsp的搜索信息
	public void saveHistory(String type, String user, String sk, String date);
	
	// 获得热门搜索和最近搜索列表
	public ArrayList<String> hotSearch();
	public ArrayList<String> newlySearch();
}
