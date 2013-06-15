package dcd.academic.DAO;

import java.util.ArrayList;

import dcd.academic.model.Gift;

public interface ShareDAO {
	// 用户分享相关数据库读取操作
	public void addGift(Gift gift);
	public ArrayList<Gift> getGift(String username); 
}
