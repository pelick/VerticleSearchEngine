package zbf.struts.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import zbf.search.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CofieldAction extends ActionSupport{
	private String field;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		
		BtwAuthor ba = new BtwAuthor();
		ArrayList<String> list = ba.findCoAuthorsByField(field, 0, 30);
		json = ba.getCoauthorJson(list);
		
		return SUCCESS;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
