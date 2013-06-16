package dcd.academic.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * 
 * @author pelick
 * 简单封装后的solrj客户端
 *
 */
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
