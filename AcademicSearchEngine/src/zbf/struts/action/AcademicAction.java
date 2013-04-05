package zbf.struts.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zbf.search.model.PublicationModel;
import zbf.search.model.ResearcherModel;
import zbf.search.solrj.SolrjHelper;
import zbf.search.util.StdOutUtil;
import zbf.struts.model.TotalListMap;

import com.opensymphony.xwork2.ActionSupport;

public class AcademicAction extends ActionSupport {

	private String key;
	private String core;
	private int start=0;
	private int rows=20;
	private long total;

	public List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
	public List<PublicationModel> paperlist = new ArrayList<PublicationModel>();
	
	public String execute(){
		SolrjHelper solr = null;
		String field = null;
		if (core.equals("core0")) {
			solr = new SolrjHelper(0);
			field = "name";
			TotalListMap map = solr.getAuthorList(field, key, start, rows);
			authorlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core1")) {
			solr = new SolrjHelper(1);
			field = "title";
			TotalListMap map = solr.getPaperList(field, key, start, rows);
			paperlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core1")) {
			solr = new SolrjHelper(2);
			// ...
		}
		return SUCCESS;
	}

	public List<ResearcherModel> getAuthorlist() {
		return authorlist;
	}

	public void setAuthorlist(List<ResearcherModel> authorlist) {
		this.authorlist = authorlist;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}
	
	public List<PublicationModel> getPaperlist() {
		return paperlist;
	}

	public void setPaperlist(List<PublicationModel> paperlist) {
		this.paperlist = paperlist;
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
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
