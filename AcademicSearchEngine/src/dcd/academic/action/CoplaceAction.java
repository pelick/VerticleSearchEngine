package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CoplaceAction extends ActionSupport{
	private String place;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		
		BtwAuthor ba = new BtwAuthor();
		ArrayList<String> list = ba.findCoAuthorsByPlace(place, 20, 30);
		json = ba.getCoauthorJson(list);
		
		return SUCCESS;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
