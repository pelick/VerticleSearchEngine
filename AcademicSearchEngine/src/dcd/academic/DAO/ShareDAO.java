package dcd.academic.DAO;

import java.util.ArrayList;

import dcd.academic.model.Gift;

public interface ShareDAO {
	public void addGift(Gift gift);
	public ArrayList<Gift> getGift(String username); 
}
