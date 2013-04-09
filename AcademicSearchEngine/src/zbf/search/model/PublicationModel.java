package zbf.search.model;

import java.util.ArrayList;

public class PublicationModel {
	private String title = "";
	private String author = "";
	private String pub_abstract = "";
	private String conference = "";
	private String view_url = "";
	private ArrayList<ResearcherModel> authors = new ArrayList<ResearcherModel>();

	public ArrayList<ResearcherModel> getAuthors() {
		return authors;
	}
	public void setAuthors(ArrayList<ResearcherModel> authors) {
		this.authors = authors;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPub_abstract() {
		return pub_abstract;
	}
	public void setPub_abstract(String pub_abstract) {
		if (pub_abstract != null)
			this.pub_abstract = pub_abstract;
	}
	public String getConference() {
		return conference;
	}
	public void setConference(String conference) {
		if (conference != null)
			this.conference = conference;
	}
	public String getView_url() {
		return view_url;
	}
	public void setView_url(String view_url) {
		if (view_url != null)
			this.view_url = view_url;
	}
	
	
}
