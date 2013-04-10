package zbf.struts.action;

import java.util.ArrayList;

import zbf.search.solrj.SolrjService;

import com.opensymphony.xwork2.ActionSupport;

public class PlaceAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	public ArrayList<String> places = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		SolrjService solrj = new SolrjService();
		places = solrj.getPlaceList(name);
		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<String> places) {
		this.places = places;
	}
}
