package zbf.search.solrj;

import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import zbf.search.util.StdOutUtil;

public class SolrjHelper {
	/**
	 * params type(0:researcher 1:pub_meta 2:pub_text)
	 */
	private int type; // 
	private SolrjClient client;
	
	SolrjHelper(int type) {
		this.type = type;
		client = new SolrjClient(type);
	}
	
	public SolrDocumentList getDocListByParams(String field, String[] q) {
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(field + ":" + q);
		query.setStart(0);
		query.setRows(10);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			return docs;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SolrDocumentList getDocListByParams(String field, String q, int start, int rows) {
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(field + ":" + q);
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			return docs;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public SolrDocumentList getDocListByParams(String field, String[] q, int start, int rows) {
//		
//	}
	
	public static void main(String[] args) {
		SolrjClient client = new SolrjClient(0);
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery("name:tom");
		query.setRows(20);
		query.setStart(0);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				StdOutUtil.out((String)resultDoc.getFieldValue("name"));
				StdOutUtil.out((String)resultDoc.getFieldValue("workplace"));
				StdOutUtil.out((String)resultDoc.getFieldValue("homepage"));
				String f = (String)resultDoc.getFieldValue("field");
				StdOutUtil.out(f.replaceAll("&amp;", ""));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
}	
