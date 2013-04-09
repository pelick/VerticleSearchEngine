package zbf.search.solrj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.auth.params.AuthParamBean;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import zbf.search.model.PaperModel;
import zbf.search.model.PublicationModel;
import zbf.search.model.ResearcherModel;
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
	
	public TotalListMap getAuthorMetaList(String field, String q, int start, int rows) {
		TotalListMap map = new TotalListMap();
		List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
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
				ResearcherModel rmodel = new ResearcherModel();
				rmodel.setName((String)resultDoc.getFieldValue("name"));
				rmodel.setWorkplace((String)resultDoc.getFieldValue("workplace"));
				rmodel.setHomepage((String)resultDoc.getFieldValue("homepage"));
				rmodel.setField((String)resultDoc.getFieldValue("field"));
				authorlist.add(rmodel);
			}
			long total = rsp.getResults().getNumFound();
			map.setList(authorlist);
			map.setTotal(total);
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
				String[] authors = author.split(",");
				pmodel.setAuthors(StringUtil.ArrayToArrayList(authors));
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
			long total = rsp.getResults().getNumFound();
			map.setList(paperlist);
			map.setTotal(total);
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
			long total = rsp.getResults().getNumFound();
			map.setList(paperfulllist);
			map.setTotal(total);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {

	}
}	
