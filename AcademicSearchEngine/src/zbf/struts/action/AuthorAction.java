package zbf.struts.action;

import java.util.ArrayList;
import java.util.List;

import zbf.search.model.ResearcherModel;
import zbf.search.solrj.SolrjHelper;

import com.opensymphony.xwork2.ActionSupport;

public class AuthorAction extends ActionSupport {

	private String key;
	private String type;
	
	public List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
	
	public String execute(){
		SolrjHelper solr = new SolrjHelper(0);
		authorlist = solr.getDocListByParams(type, key);
	
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
