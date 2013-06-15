package dcd.academic.action;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.recommend.BtwAuthor;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author pelick
 * 可视化ajax，寻找某一工作地的共同发表者
 *
 */
public class CofieldAction extends ActionSupport{
	// 前端传参
	private String field;
	
	// 传回的计算后的json数据串，给前端可视化显示
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		// 先去数据库找有没有之前坐过这次计算并已经保存在mysql
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		String s = dao.getDiscover(field, "field");
		if ( s != null) {
			json = JSONObject.fromObject(s);
		} else {
			// 数据库内没有，利用相关类进行计算，并保存mysql
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
