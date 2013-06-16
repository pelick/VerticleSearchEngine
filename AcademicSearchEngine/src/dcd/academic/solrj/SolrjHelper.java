package dcd.academic.solrj;

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
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import dcd.academic.model.PaperModel;
import dcd.academic.model.PublicationModel;
import dcd.academic.model.ResearcherModel;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;
import dcd.academic.model.TotalListMap;

/**
 * 
 * @author pelick
 * 一个重要的solrj类，定制并提供了大部分ajax和action的搜索需求
 *
 */
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
	
	// 得到一个学者的tag list
	public String getAuthorTags(String name) {
		String tags = "";
		SolrServer server = client.getSolrServer();
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
				tags = (String)resultDoc.getFieldValue("moretags");
				break;
			}
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	// 用于index.jsp里的学者搜索。传入name参数，以及附加的研究领域和工作地参数，得到一个学者的集合，每个学者包含了基本元数据信息。
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
		query.setHighlight(true);
		query.addHighlightField("name");
		query.setHighlightSimplePre("<strong>");
		query.setHighlightSimplePost("</strong>");
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
	
	// 用于index.jsp里的论文搜索
	public TotalListMap getPaperMetaList(String field, String q, int start, int rows) {
		TotalListMap map = new TotalListMap();
		List<PublicationModel> paperlist = new ArrayList<PublicationModel>();
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();

		query.setQuery(StringUtil.transformQuery(field, q));
		query.setHighlight(true);
		query.addHighlightField("title");
		query.addHighlightField("pub_abstract");
		query.setHighlightSimplePre("<strong>");
		query.setHighlightSimplePost("</strong>");
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
	
	// 用于index.jsp里的全文搜索
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
	
	// 传入name，得到学者元数据信息
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
				author.setMoretags((String)resultDoc.getFieldValue("moretags"));
				break;
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return author;
	}
	
	// 传入title得到论文元数据信息
	public PublicationModel getPaperInfo(String title) {
		PublicationModel paper = null;
		SolrServer server = client.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(StringUtil.transformQuery("title", title));
		query.setStart(0);
		query.setRows(1);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				paper = new PublicationModel();
				paper.setTitle((String)resultDoc.getFieldValue("title"));
				paper.setAuthor((String)resultDoc.getFieldValue("author"));
				paper.setConference((String)resultDoc.getFieldValue("conference"));
				paper.setPub_abstract((String)resultDoc.getFieldValue("pub_abstract"));
				break;
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return paper;
	}
	
	// 传入name，得到该学者的所有论文的标题和摘要集合
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
				s = s + " " + (String)resultDoc.getFieldValue("pub_abstract");
				s = s + " " + (String)resultDoc.getFieldValue("title");
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		List<String> array = StringUtil.getTokens(s);
		return array;
	}
	
	// 传入name，得到该学者相关的所有论文集合
	public List<String> getAuthorPubs(String name, int start, int rows) throws IOException {
		List<String> array = new ArrayList<String>();
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
				String s = "";
				s = s + (String)resultDoc.getFieldValue("pub_abstract");
				s = s + " " + (String)resultDoc.getFieldValue("title");
				array.add(s);
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	// 传入title里的关键字，得到一个论文列表
	public List<String> getPubsByTitle(String text, int start, int rows) throws IOException {
		List<String> array = new ArrayList<String>();
		SolrjClient newclient = new SolrjClient(1);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(StringUtil.transformQuery("title", text));
		query.setStart(start);
		query.setRows(rows);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			Iterator<SolrDocument> it = docs.iterator();
			while (it.hasNext()) {
				SolrDocument resultDoc = it.next();
				String s = "";
				s = s + (String)resultDoc.getFieldValue("pub_abstract");
				s = s + " " + (String)resultDoc.getFieldValue("title");
				array.add(s);
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	// 用于判断是否存在该学者的信息
	public boolean existAuthor(String name) throws IOException {
		boolean is = false;
		SolrjClient newclient = new SolrjClient(0);
		SolrServer server = newclient.getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(StringUtil.transformQuery("name", name));
		QueryResponse rsp;
		try {
			rsp = server.query(query);
			if (rsp.getResults().getNumFound() > 0) {
				is = true;
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	public static void main(String[] args) throws IOException {
		SolrjHelper sh = new SolrjHelper(0);
		sh.getAuthorTags("Andrew Y. Ng");
	}
}	
