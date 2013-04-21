package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CoauthorAction extends ActionSupport{
	private String name;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		
		BtwAuthor ba = new BtwAuthor();
		ArrayList<String> list = ba.findCoAuthorsByName(name, 0, 50);
		json = ba.getCoauthorJson(list);
		
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	
}
