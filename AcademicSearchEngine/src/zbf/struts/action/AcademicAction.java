package zbf.struts.action;

import java.util.ArrayList;
import java.util.List;

import zbf.search.model.PaperModel;
import zbf.search.model.PublicationModel;
import zbf.search.model.ResearcherModel;
import zbf.search.solrj.SolrjHelper;
import zbf.struts.model.TotalListMap;

import com.opensymphony.xwork2.ActionSupport;

public class AcademicAction extends ActionSupport {

	private String key;
	private String core;
	private int start = 0;
	private int rows = 20;
	private long total;
	
	private String nextUrl;
	private String preUrl;
	private int curNum;
	private int curPage;
	private int allPage;

	public List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
	public List<PublicationModel> paperlist = new ArrayList<PublicationModel>();
	public List<PaperModel> paperfulllist = new ArrayList<PaperModel>();

	public String execute(){
		SolrjHelper solr = null;
		String field = null;

		if (core.equals("core0")) {
			solr = new SolrjHelper(0);
			field = "name";
			TotalListMap map = solr.getAuthorMetaList(field, key, start, rows);
			authorlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core1")) {
			solr = new SolrjHelper(1);
			field = "title";
			TotalListMap map = solr.getPaperMetaList(field, key, start, rows);
			paperlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core2")) {
			solr = new SolrjHelper(2);
			field = "text";
			TotalListMap map = solr.getPaperFullList(field, key, start, rows);
			paperfulllist = map.getList();
			total = map.getTotal();
		}
		
		if (start + rows <= total) {
			nextUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+"&start="+(start+rows)+"&rows="+rows;
		} else {
			nextUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+"&start="+(start)+"&rows="+rows;
		}
		if (start - rows < 0) {
			preUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+"&start="+(start)+"&rows="+rows;
		} else {
			preUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+"&start="+(start-rows)+"&rows="+rows;
		}
		
		curNum = (int) ((start+rows) > total? total : (start+rows));
		curPage = start/rows + 1;
		allPage = (int) (total/rows + 1);
		
		return SUCCESS;
	}

	public List<PaperModel> getPaperfulllist() {
		return paperfulllist;
	}

	public void setPaperfulllist(List<PaperModel> paperfulllist) {
		this.paperfulllist = paperfulllist;
	}
	
	public int getCurNum() {
		return curNum;
	}

	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
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
	
	public String getNextUrl() {
		return nextUrl;
	}

	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	public String getPreUrl() {
		return preUrl;
	}

	public void setPreUrl(String preUrl) {
		this.preUrl = preUrl;
	}
	
}
