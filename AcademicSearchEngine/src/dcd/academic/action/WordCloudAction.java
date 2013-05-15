package dcd.academic.action;

import java.util.ArrayList;
import java.util.List;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public class WordCloudAction extends ActionSupport {

	private String name;
	private String user;
	private int start = 0;
	private int rows = 20;
	
	public String[] abs;
	
	@Override
	public String execute() throws Exception {
		System.out.println("[user]: "+user);
		System.out.println("[name]: "+name);
		
		if (user == null && name != null) {
			SolrjHelper solrj = new SolrjHelper(1);
			List<String> list = solrj.getAuthorAbstraction(name, start, rows);
			abs = StringUtil.removeDupl(list);
		} else if (user != null && name == null) {
			ArrayList<String> list = DAOfactory.getUserDAO().getUserKeys(user);
			abs = list.toArray(new String[list.size()]);
		}
		return SUCCESS;
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String[] getAbs() {
		return abs;
	}

	public void setAbs(String[] abs) {
		this.abs = abs;
	}
}
