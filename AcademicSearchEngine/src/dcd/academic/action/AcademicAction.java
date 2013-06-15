package dcd.academic.action;

import java.util.ArrayList;
import java.util.List;

import dcd.academic.model.PaperModel;
import dcd.academic.model.PublicationModel;
import dcd.academic.model.ResearcherModel;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.model.TotalListMap;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author pelick
 * 对应index.jsp的三个主要搜索功能。core0，core1，core2分别对应学者元数据，论文元数据，全文
 */
public class AcademicAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	// 前端传参
	private String key;
	private String core;
	private String field = "";
	private String workplace = "";
	
	// 默认
	private int start = 0;
	private int rows = 20;
	private long total;
	
	// 返回到index.jsp页面的参数
	private String nextUrl;
	private String preUrl;
	private int curNum;
	private int curPage;
	private int allPage;
	public List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
	public List<PublicationModel> paperlist = new ArrayList<PublicationModel>();
	public List<PaperModel> paperfulllist = new ArrayList<PaperModel>();

	public String execute(){
		
		// 封装后的solr类
		SolrjHelper solr = null;
		/**
		 * 根据core的不同取值，采取不同的搜索接口
		 */
		if (core.equals("core0")) {
			solr = new SolrjHelper(0);
			TotalListMap map = solr.getAuthorMetaList("name", key, field, workplace, start, rows);
			authorlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core1")) {
			solr = new SolrjHelper(1);
			TotalListMap map = solr.getPaperMetaList("title", key, start, rows);
			paperlist = map.getList();
			total = map.getTotal();
		} else if (core.equals("core2")) {
			solr = new SolrjHelper(2);
			TotalListMap map = solr.getPaperFullList("text", key, start, rows);
			paperfulllist = map.getList();
			total = map.getTotal();
		}
		
		// condition给搜索请求参数增加了研究领域或工作地的限制，本质是加了搜索参数
		String condition = null;
		if (field.equals("") && workplace.equals("")) {
			condition = "";
		} else if (workplace.equals("")) {
			condition = "&field="+field.replaceAll(" ", "+");
		} else if (field.equals("")) {
			condition = "&workplace="+workplace.replaceAll(" ", "+");
		} else {
			condition = "&field="+field.replaceAll(" ", "+")+"&workplace="+workplace.replaceAll(" ", "+");
		}
		
		// 给翻页参数赋值，翻页是另一次搜索请求，区别在于start值
		if (start + rows <= total) {
			nextUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+condition+"&start="+(start+rows)+"&rows="+rows;
		} else {
			nextUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+condition+"&start="+(start)+"&rows="+rows;
		}
		if (start - rows < 0) {
			preUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+condition+"&start="+(start)+"&rows="+rows;
		} else {
			preUrl = "academic?key="+key.replaceAll(" ", "+")+"&core="+core+condition+"&start="+(start-rows)+"&rows="+rows;
		}
		
		curNum = (int) ((start+rows) > total? total : (start+rows));
		curPage = start/rows + 1;
		allPage = (int) (total/rows + 1);
		
		return SUCCESS;
	}
	
	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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
