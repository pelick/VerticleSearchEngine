package zbf.struts.action;

import java.util.ArrayList;
import java.util.List;

import zbf.search.model.ResearcherModel;

import com.opensymphony.xwork2.ActionSupport;

public class AuthorAction extends ActionSupport {
	
	public List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
	
	public String execute(){
		
		return SUCCESS;
	}

	public List<ResearcherModel> getAuthorlist() {
		return authorlist;
	}

	public void setAuthorlist(List<ResearcherModel> authorlist) {
		this.authorlist = authorlist;
	}
}
