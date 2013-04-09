package zbf.struts.action;

import java.util.ArrayList;

import zbf.search.solrj.SolrjService;

import com.opensymphony.xwork2.ActionSupport;

public class FieldAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	public ArrayList<String> fields = new ArrayList<String>();
	
	@Override
	public String execute() throws Exception {
		
		SolrjService solrj = new SolrjService();
		fields = solrj.getFieldList(name);
		return SUCCESS;
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
}
