package zbf.struts.action;

import java.util.ArrayList;

import zbf.search.model.ResearcherModel;
import zbf.search.solrj.SolrjAjaxService;

import com.opensymphony.xwork2.ActionSupport;

public class RelatedAction extends ActionSupport {

	private String name;
	private String field;
	private String place;
	
	public ArrayList<ResearcherModel> researcherlist = new ArrayList<ResearcherModel>();
	
	@Override
	public String execute() throws Exception {
		SolrjAjaxService sas = new SolrjAjaxService();
		researcherlist = sas.getRelatedResearchers(name, field, place);
		return SUCCESS;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ArrayList<ResearcherModel> getResearcherlist() {
		return researcherlist;
	}

	public void setResearcherlist(ArrayList<ResearcherModel> researcherlist) {
		this.researcherlist = researcherlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
