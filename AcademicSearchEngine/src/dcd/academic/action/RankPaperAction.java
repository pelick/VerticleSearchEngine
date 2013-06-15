package dcd.academic.action;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import dcd.academic.model.PublicationModel;
import dcd.academic.recommend.BtwPublication;
import dcd.academic.recommend.PageRank;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.model.TotalListMap;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author pelick
 * 利用pagerank对学者的论文进行重排序，用于researcher.jsp里论文展示
 *
 */
public class RankPaperAction extends ActionSupport {
	
	private String name;
	private int start = 0;
	private int rows = 50;
	
	public List<PublicationModel> ranklist = new ArrayList<PublicationModel>();

	@Override
	public String execute() throws Exception {
		// 首先计算得到该学者的论文元数据列表
		SolrjHelper solr = new SolrjHelper(1);
		TotalListMap map = solr.getPaperMetaList("author", name, start, rows);
		List<PublicationModel> tmplist = map.getList();
		// 读出论文列表里的标题和摘要两部分文本
		List<String> pubs = new ArrayList<String>();
		for (PublicationModel model : tmplist) {
			pubs.add(model.getTitle()+" "+model.getPub_abstract());
		}
		// 利用pagerank相关类进行计算和游走
		BtwPublication bp = new BtwPublication();
		PageRank pageRank = new PageRank(bp.getPagerankS(pubs));
		// 得到新的rank列表，处理后返回给前端
		List<Double> ranks = pageRank.doPagerank();
		TreeMap<Double, PublicationModel> rankmap = new TreeMap<Double, PublicationModel>();
		for (int i = 0; i < ranks.size(); i ++) {
			rankmap.put(ranks.get(i), tmplist.get(i));
		}
		while (rankmap.size()>0) {
			ranklist.add(rankmap.pollLastEntry().getValue());
		}
		
		return SUCCESS;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PublicationModel> getRanklist() {
		return ranklist;
	}

	public void setRanklist(List<PublicationModel> ranklist) {
		this.ranklist = ranklist;
	}
	
}
