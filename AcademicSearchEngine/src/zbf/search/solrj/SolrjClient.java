package zbf.search.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class SolrjClient {
	private int core;
	private final String SOLR = "http://localhost:9080/solr/core";
	
	public SolrjClient(int core) {
		this.core = core;
	}
	
	public SolrServer getSolrServer() {
		String url = SOLR + core;
		SolrServer server = new HttpSolrServer(url);
		return server;
	}
}
