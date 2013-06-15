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
 * 可视化ajax，寻找某位学者的共同发表者
 *
 */
public class CoauthorAction extends ActionSupport{
	// 前端传参
	private String name;
	
	// 传回的计算后的json数据串，给前端可视化显示
	public JSONObject json;
	
	@Override
	public String execute() throws Exception {
		// 先去数据库找有没有之前坐过这次计算并已经保存在mysql
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		String s = dao.getDiscover(name, "author");
		if ( s != null) {
			// 数据库内有，直接读出
			json = JSONObject.fromObject(s);
		} else {
			// 数据库内没有，利用相关类进行计算，并保存mysql
			BtwAuthor ba = new BtwAuthor();
			ArrayList<String> list = ba.findCoAuthorsByName(name, 0, 50);
			json = ba.getCoauthorJson(list);
			dao.saveDiscover(name, "author", json.toString());
		}
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
