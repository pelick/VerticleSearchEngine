package zbf.search.solrj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import zbf.search.model.ResearcherModel;

public class SolrjHelper {
	/**
	 * params type(0:researcher 1:pub_meta 2:pub_text)
	 */
	private int type; // 
	private SolrjClient client;
	
	private final int START = 0;
	private final int ROWS = 20;
	
	public SolrjHelper(int type) {
		this.type = type;
		client = new SolrjClient(type);
	}
	
	public List<ResearcherModel> getDocListByParams(String field, String q) {
		List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(field + ":" + q);
		query.setStart(START);
		query.setRows(ROWS);
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
				String f = (String)resultDoc.getFieldValue("field");
				rmodel.setField(f.replaceAll("&amp;", ""));
				authorlist.add(rmodel);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return authorlist;
	}
	
	public List<ResearcherModel> getDocListByParams(String field, String q, int start) {
		List<ResearcherModel> authorlist = new ArrayList<ResearcherModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(field + ":" + q);
		query.setStart(start);
		query.setRows(ROWS);
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
				String f = (String)resultDoc.getFieldValue("field");
				rmodel.setField(f.replaceAll("&amp;", ""));
				authorlist.add(rmodel);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return authorlist;
	}
	
	
//	public SolrDocumentList getDocListByParams(String field, String[] q, int start, int rows) {
//		
//	}
	
	public static void main(String[] args) {

	}
}	
