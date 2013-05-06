package dcd.academic.solrj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import dcd.academic.model.ResearcherModel;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

public class SolrjAjaxService {
	
	public List<ArrayList<String>> getFieldPlaceList(String name, String field, String workplace) {
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> fieldlist = new ArrayList<String>();
		ArrayList<String> placelist = new ArrayList<String>();
		SolrjClient newclient = new SolrjClient(0);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		
		if (field.equals("") && workplace.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", name));
		} else if (workplace.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", name)+" "+StringUtil.transformQuery("field", field));
		} else if (field.equals("")) {
			query.setQuery(StringUtil.transformQuery("name", name)+" "+StringUtil.transformQuery("workplace", workplace));
		} else {
			query.setQuery(StringUtil.transformQuery("name", name)+" "+StringUtil.transformQuery("field", field)+" "+StringUtil.transformQuery("workplace", workplace));
		}
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				String tmp = (String)resultDoc.getFieldValue("field");
				String[] tmps = tmp.split(", ");
				for (String s : tmps) {
					if (!fieldlist.contains(s)) {
						fieldlist.add(s);
					}
				}
				String s = (String)resultDoc.getFieldValue("workplace");
				if (!placelist.contains(s)) {
					placelist.add(s);
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		list.add(fieldlist);
		list.add(placelist);
		return list;
	}
	
	public ArrayList<ResearcherModel> getRelatedResearchers(String name, String field, String place) {
		SolrjClient newclient = new SolrjClient(0);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		//query.setQuery(StringUtil.transformQuery("field", field) + " " + StringUtil.transformQuery("workplace", place));
		query.setQuery(StringUtil.transformQuery("field", field));
		query.setStart(0);
		query.setRows(15);
		
		QueryResponse rsp;
		ArrayList<ResearcherModel> list = new ArrayList<ResearcherModel>();
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			StdOutUtil.out(rsp.getResults().getNumFound());
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				ResearcherModel tmp = new ResearcherModel();
				if (!resultDoc.getFieldValue("name").equals(name)) {
					tmp.setName((String)resultDoc.getFieldValue("name"));
					tmp.setField((String)resultDoc.getFieldValue("field"));
					tmp.setWorkplace((String)resultDoc.getFieldValue("workplace"));
					tmp.setPicurl((String)resultDoc.getFieldValue("picurl"));
					tmp.setPicurl((String)resultDoc.getFieldValue("moretags"));
					list.add(tmp);
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		SolrjAjaxService sas = new SolrjAjaxService();
		sas.getRelatedResearchers("", "", "");
	}
}
