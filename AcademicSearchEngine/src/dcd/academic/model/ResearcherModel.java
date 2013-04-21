package dcd.academic.model;

public class ResearcherModel {
	private String name = "";
	private String microul = "";
	private String workplace = "";
	private String field = "";
	private String homepage = "";
	private String keywords_url = "";
	private String publications_url = "";
	private String citations_url = "";
	private String picurl = "";
	
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMicroul() {
		return microul;
	}
	public void setMicroul(String microul) {
		this.microul = microul;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		if (workplace != null)
			this.workplace = workplace;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		if (homepage != null)
			this.homepage = homepage;
	}
	public String getKeywords_url() {
		return keywords_url;
	}
	public void setKeywords_url(String keywords_url) {
		this.keywords_url = keywords_url;
	}
	public String getPublications_url() {
		return publications_url;
	}
	public void setPublications_url(String publications_url) {
		this.publications_url = publications_url;
	}
	public String getCitations_url() {
		return citations_url;
	}
	public void setCitations_url(String citations_url) {
		this.citations_url = citations_url;
	}
	
	
}
