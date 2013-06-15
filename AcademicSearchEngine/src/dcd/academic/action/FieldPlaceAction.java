package dcd.academic.action;

import java.util.ArrayList;
import java.util.List;

import dcd.academic.solrj.SolrjAjaxService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author pelick
 * index.jsp搜索页左侧的统计结果，分别返回研究领域和工作地两个list
 *
 */
public class FieldPlaceAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String field = "";
	private String workplace = "";
	
	public ArrayList<String> fields = new ArrayList<String>();
	public ArrayList<String> places = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		
		SolrjAjaxService solrj = new SolrjAjaxService();
		
		List<ArrayList<String>> list = solrj.getFieldPlaceList(name, field, workplace);
		fields = list.get(0);
		places = list.get(1);
		
		return SUCCESS;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getFields() {
		return fields;
	}

	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}

	public ArrayList<String> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<String> places) {
		this.places = places;
	}
	
}
