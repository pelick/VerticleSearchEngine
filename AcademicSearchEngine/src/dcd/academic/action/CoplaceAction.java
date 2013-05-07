package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

public class CoplaceAction extends ActionSupport{
	private String place;
	
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		String s = dao.getDiscover(place, "place");
		if ( s != null) {
			json = JSONObject.fromObject(s);
		} else {
			BtwAuthor ba = new BtwAuthor();
			ArrayList<String> list = ba.findCoAuthorsByPlace(place, 0, 30);
			json = ba.getCoauthorJson(list);
			dao.saveDiscover(place, "place", json.toString());
		}
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
