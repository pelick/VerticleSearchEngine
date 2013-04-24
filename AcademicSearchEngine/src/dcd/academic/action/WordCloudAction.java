package dcd.academic.action;

import java.util.List;

import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

public class WordCloudAction extends ActionSupport {

	private String name;
	private int start = 0;
	private int rows = 10;
	
	public String[] abs;
	
	@Override
	public String execute() throws Exception {
		SolrjHelper solrj = new SolrjHelper(1);
		List<String> list = solrj.getAuthorAbstraction(name, start, rows);
		abs = StringUtil.removeDupl(list);
		return SUCCESS;
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
