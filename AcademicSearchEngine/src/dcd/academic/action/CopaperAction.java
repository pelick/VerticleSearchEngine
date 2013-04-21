package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CopaperAction extends ActionSupport{
	private String text;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		
		BtwAuthor ba = new BtwAuthor();
		ArrayList<String> list = ba.findCoAuthorsByPaper(text, 0, 50);
		json = ba.getCoauthorJson(list);
		
		return SUCCESS;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	
}
