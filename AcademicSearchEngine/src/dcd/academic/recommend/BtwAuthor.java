package dcd.academic.recommend;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import net.sf.json.JSONObject;

import dcd.academic.solrj.SolrjClient;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

/**
 * 
 * @author pelick
 * 学者之间相关的一些查询操作
 *
 */
public class BtwAuthor {
	public static void main(String[] args) throws IOException {
		BtwAuthor ba = new BtwAuthor();
		//ArrayList<String> list = ba.findCoAuthorsByName("Noga Alon", 0, 300);
		ArrayList<String> list = ba.findCoAuthorsByPaper("machine learning", 0, 50);
		StdOutUtil.out(ba.getCoauthorJson(list)); 
	}
	
	// discover模块请求之一，通过论文内容学者
	public ArrayList<String> findCoAuthorsByPaper(String text, int start, int rows) throws UnknownHostException {
		ArrayList<String> coworkers = new ArrayList<String>();
		SolrjClient client = new SolrjClient(1);
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery((StringUtil.transformQuery("title", text))+" "+(StringUtil.transformQuery("pub_abstract", text)));
		query.setStart(start);
		query.setRows(rows);
		
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			for (SolrDocument doc : docs) {
				String s = (String) doc.getFieldValue("author");
				String[] ss = s.split(", ");
				for (String tmp : ss) {
					if (!coworkers.contains(tmp))
						coworkers.add(tmp);
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Collections.sort(coworkers);
		StdOutUtil.out(coworkers.size());
		return coworkers;
	}
	
	// discover模块请求之一，通过学者名字找学者自己的共同发表论文的学者列表
	public ArrayList<String> findCoAuthorsByName(String name, int start, int rows) throws UnknownHostException {
		ArrayList<String> coworkers = new ArrayList<String>();
		SolrjClient client = new SolrjClient(1);
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery((StringUtil.transformQuery("author", name)));
		query.setStart(start);
		query.setRows(rows);
		
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			for (SolrDocument doc : docs) {
				String s = (String) doc.getFieldValue("author");
				String[] ss = s.split(", ");
				for (String tmp : ss) {
					if (!coworkers.contains(tmp)) {
						coworkers.add(tmp);
					}
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Collections.sort(coworkers);
		StdOutUtil.out(coworkers.size());
		return coworkers;
	}
	
	// discover模块请求之一，通过研究领域找学者集合
	public ArrayList<String> findCoAuthorsByField(String field, int start, int rows) throws UnknownHostException {
		ArrayList<String> coworkers = new ArrayList<String>();
		SolrjClient client = new SolrjClient(0);
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery((StringUtil.transformQuery("field", field)));
		query.setStart(start);
		query.setRows(rows);
		
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			for (SolrDocument doc : docs) {
				String s = (String) doc.getFieldValue("name");
				coworkers.add(s);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Collections.sort(coworkers);
		StdOutUtil.out(coworkers.size());
		return coworkers;
	}
	
	// discover模块请求之一，通过工作地寻找一个学者集合
	public ArrayList<String> findCoAuthorsByPlace(String place, int start, int rows) throws UnknownHostException {
		ArrayList<String> coworkers = new ArrayList<String>();
		SolrjClient client = new SolrjClient(0);
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery((StringUtil.transformQuery("workplace", place)));
		query.setStart(start);
		query.setRows(rows);
		
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			for (SolrDocument doc : docs) {
				String s = (String) doc.getFieldValue("name");
				coworkers.add(s);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		Collections.sort(coworkers);
		StdOutUtil.out(coworkers.size());
		return coworkers;
	}
	
	// 计算矩阵图和力导向图数据串里的node
	public Map<String, Integer> coauthorNodes(ArrayList<String> list) throws UnknownHostException{
		Map<String, String> nameMap = new HashMap<String, String>();		
		Map<String, Integer> placeMap = new HashMap<String, Integer>();
		Map<String, Integer> nodeMap = new TreeMap<String, Integer>();
		SolrjClient client = new SolrjClient(0);
		SolrServer server = client.getSolrServer();
		for (String name : list) {
			SolrQuery query = new SolrQuery();
			query.setQuery((StringUtil.transformQuery("name", name)));
			query.setStart(0);
			query.setRows(1);
			QueryResponse rsp;
			try {
				rsp = server.query(query);
				SolrDocumentList docs = rsp.getResults();
				for (SolrDocument doc : docs) {
					String workplace = (String) doc.getFieldValue("workplace");
					nameMap.put(name, workplace);
					if(!placeMap.containsKey(workplace)) {
						placeMap.put(workplace, placeMap.keySet().size());
					}
					nodeMap.put(name, placeMap.get(nameMap.get(name)));
					break;
				}
			} catch (SolrServerException e) {
				e.printStackTrace();
			}
		}
		StdOutUtil.out("[nodeMap]: "+nodeMap.toString());
		StdOutUtil.out("[nodeMap]: "+nodeMap.keySet().size());
		return nodeMap;
	}
	
	// 计算矩阵图和力导向图数据串里的links
	public Map<Integer[], Integer> coauthorLinks(Map<String, Integer> nodeMap) throws IOException {
		Map<Integer[], Integer> linkMap = new HashMap<Integer[], Integer>();
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		
		Iterator<String> iterator = nodeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String s = iterator.next();
			numMap.put(s, numMap.size());
		}
		
		// find co-pdfs-count
		Set<String> list = numMap.keySet();
		for (String name : list) {
			int i = numMap.get(name);
			for (String other : list) {
				if (other != name) {
					SolrjClient client = new SolrjClient(1);
					SolrServer server = client.getSolrServer();
					SolrQuery query = new SolrQuery();
					query.setQuery((StringUtil.transformQuery("author", name+" "+other)));
					QueryResponse rsp;
					try {
						rsp = server.query(query);
						SolrDocumentList docs = rsp.getResults();
						if (docs.size() > 0) {
							String[] s = {name, other};
							Integer[] array = {i, numMap.get(other)};
							linkMap.put(array, docs.size());
							StdOutUtil.out(docs.size());
						}
					} catch (SolrServerException e) {
						e.printStackTrace();
					}
				}
			}
		}
		StdOutUtil.out("[linkMap]: "+linkMap.size());
		return linkMap;
	}
	
	// 把计算出来的nodes和links整合成一个json数据串返回给前台进行可视化展示
	public JSONObject getCoauthorJson(ArrayList<String> list) throws IOException {
		BtwAuthor author = new BtwAuthor();
		Map<String, Integer> nodeMap = author.coauthorNodes(list);
		Map<Integer[], Integer> linkMap = author.coauthorLinks(nodeMap);
		JSONObject json = new JSONObject();
		
		Iterator<String> iterator1 = nodeMap.keySet().iterator();
		List<JSONObject> jsonlist1 = new ArrayList<JSONObject>();
		while (iterator1.hasNext()) {
			String name = iterator1.next();
			int group = nodeMap.get(name);
			JSONObject tmp = new JSONObject();
			tmp.put("name", name);
			tmp.put("group", group);
			jsonlist1.add(tmp);
		}
		json.put("nodes", jsonlist1);
	
		
		Iterator<Integer[]> iterator2 = linkMap.keySet().iterator();
		List<JSONObject> jsonlist2 = new ArrayList<JSONObject>();
		while (iterator2.hasNext()) {
			Integer[] ns = iterator2.next();
			int value = linkMap.get(ns);
			JSONObject tmp = new JSONObject();
			tmp.put("source", ns[0]);
			tmp.put("target", ns[1]);
			tmp.put("value", value);
			jsonlist2.add(tmp);
		}
		json.put("links", jsonlist2);
		return json;
	}
	
}
