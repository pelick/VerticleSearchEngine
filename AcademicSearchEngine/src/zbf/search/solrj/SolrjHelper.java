package zbf.search.solrj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import zbf.search.model.PaperModel;
import zbf.search.model.PublicationModel;
import zbf.search.model.ResearcherModel;
import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;
import zbf.struts.model.TotalListMap;

public class SolrjHelper {
	/**
	 * params type(0:researcher 1:pub_meta 2:pub_text)
	 */
	private int type; // 
	private SolrjClient client;
	
	public SolrjHelper(int type) {
		this.type = type;
		client = new SolrjClient(type);
	}
	
	public TotalListMap getAuthorMetaList(String field, String q, String field_key, String workplace, int start, int rows) {
		TotalListMap map = new TotalListMap();
		List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		
		if (field_key.equals("") && workplace.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", q));
		} else if (workplace.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", q)+" "+StringUtil.transformQuery("field", field_key));
		} else if (field_key.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", q)+" "+StringUtil.transformQuery("workplace", workplace));
		} else {
			query.setQuery(StringUtil.transformQuery("name", q)+" "+StringUtil.transformQuery("field", field_key)+" "+StringUtil.transformQuery("workplace", workplace));
		}
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				ResearcherModel rmodel = new ResearcherModel();
				rmodel.setName((String)resultDoc.getFieldValue("name"));
				rmodel.setWorkplace((String)resultDoc.getFieldValue("workplace"));
				rmodel.setHomepage((String)resultDoc.getFieldValue("homepage"));
				rmodel.setField((String)resultDoc.getFieldValue("field"));
				rmodel.setPicurl((String)resultDoc.getFieldValue("picurl"));
				authorlist.add(rmodel);
			}
			map.setTotal(rsp.getResults().getNumFound());
			map.setList(authorlist);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public TotalListMap getPaperMetaList(String field, String q, int start, int rows) {
		TotalListMap map = new TotalListMap();
		List<PublicationModel> paperlist = new ArrayList<PublicationModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();

		query.setQuery(StringUtil.transformQuery(field, q));
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				PublicationModel pmodel = new PublicationModel();
				pmodel.setTitle((String)resultDoc.getFieldValue("title"));
				String author = (String)resultDoc.getFieldValue("author");
				pmodel.setAuthor(author);
				String[] authors = author.split(", ");
				ArrayList<ResearcherModel> list = new ArrayList<ResearcherModel>();
				for (int i = 0; i < authors.length; i ++) {
					list.add(getAuthorInfo(authors[i]));
				}

				pmodel.setAuthors(list);
				pmodel.setPub_abstract((String)resultDoc.getFieldValue("pub_abstract"));
				pmodel.setConference((String)resultDoc.getFieldValue("conference"));
				String url = (String)resultDoc.getFieldValue("view_url");
				if (url.startsWith("http")) {
					pmodel.setView_url(url);
				} else {
					pmodel.setView_url(null);
				}
				paperlist.add(pmodel);
			}
			map.setTotal(rsp.getResults().getNumFound());
			map.setList(paperlist);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public TotalListMap getPaperFullList(String field, String q, int start, int rows) {
		TotalListMap map = new TotalListMap();
		List<PaperModel> paperfulllist = new ArrayList<PaperModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		
		query.setQuery(StringUtil.transformQuery(field, q));
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				PaperModel pmodel = new PaperModel();
				pmodel.setName((String)resultDoc.getFieldValue("name"));
				pmodel.setTitle((String)resultDoc.getFieldValue("title"));
				pmodel.setUrl((String)resultDoc.getFieldValue("url"));
				paperfulllist.add(pmodel);
			}
			map.setTotal(rsp.getResults().getNumFound());
			map.setList(paperfulllist);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public ResearcherModel getAuthorInfo(String name) {
		ResearcherModel author = null;
		SolrjClient newclient = new SolrjClient(0);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(StringUtil.transformQuery("name", name));
		query.setStart(0);
		query.setRows(1);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				author = new ResearcherModel();
				author.setName((String)resultDoc.getFieldValue("name"));
				author.setWorkplace((String)resultDoc.getFieldValue("workplace"));
				author.setHomepage((String)resultDoc.getFieldValue("homepage"));
				author.setField((String)resultDoc.getFieldValue("field"));
				author.setPicurl((String)resultDoc.getFieldValue("picurl"));
				break;
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return author;
	}

	public List<String> getAuthorAbstraction(String name, int start, int rows) throws IOException {
		String s = "";
		SolrjClient newclient = new SolrjClient(1);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(StringUtil.transformQuery("author", name));
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				//s = s + " " + (String)resultDoc.getFieldValue("pub_abstract");
				s = s + " " + (String)resultDoc.getFieldValue("title");
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		// filter 
		String[] filter = {"that","this","what","which","they","their","with","will","these","there","into","such","under","have","which"};
		List<String> array = StringUtil.getTokens(s);
		for (String tmp : filter) {
			if (array.contains(tmp)) {
				array.remove(tmp);
			}
		}
		StdOutUtil.out(array.toString());
		return array;
	}
	
	public static void main(String[] args) throws IOException {
		SolrjHelper sh = new SolrjHelper(1);
		sh.getAuthorAbstraction("Andrew Y. Ng", 0, 15);
	}
}	
