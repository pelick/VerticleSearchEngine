package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CofieldAction extends ActionSupport{
	private String field;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		String s = dao.getDiscover(field, "field");
		if ( s != null) {
			json = JSONObject.fromObject(s);
		} else {
			BtwAuthor ba = new BtwAuthor();
			ArrayList<String> list = ba.findCoAuthorsByField(field, 0, 30);
			json = ba.getCoauthorJson(list);
			dao.saveDiscover(field, "field", json.toString());
		}
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
