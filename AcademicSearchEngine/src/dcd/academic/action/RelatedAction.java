package dcd.academic.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import dcd.academic.model.ResearcherModel;
import dcd.academic.recommend.CosineDis;
import dcd.academic.solrj.SolrjAjaxService;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author pelick
 * 得到某位学者的同研究领域或同工作地的相关学者集合，用于research.jsp里右侧的学者推荐列表
 *
 */
public class RelatedAction extends ActionSupport {

	private String name;
	private String field;
	private String place;
	
	public ArrayList<ResearcherModel> researcherlist = new ArrayList<ResearcherModel>();
	
	@Override
	public String execute() throws Exception {
		SolrjAjaxService sas = new SolrjAjaxService();
		String[] fs = field.split(",");
		ArrayList<ResearcherModel> listToRank = new ArrayList<ResearcherModel>();
		for (String tmp : fs) {
			ArrayList<ResearcherModel> tmplist = sas.getRelatedResearchers(name, tmp, place);
			for (ResearcherModel model : tmplist) {
				if (!listToRank.contains(model)) {
					listToRank.add(model);
				}
			}
		}
		researcherlist = listToRank;
//		SolrjHelper helper = new SolrjHelper(0);
//		ArrayList<String> tags = StringUtil.getTokens(helper.getAuthorTags(name));
//		
//		TreeMap<Double, ResearcherModel> map = new TreeMap<Double, ResearcherModel>();
//		for (ResearcherModel rm : listToRank) {
//			double tag_simi = CosineDis.getSimilarity(tags, StringUtil.getTokens(rm.getMoretags()));
//			//double field_simi = CosineDis.getSimilarity(StringUtil.getTokens(field), StringUtil.getTokens(rm.getField()));
//			map.put(tag_simi, rm);
//			StdOutUtil.out(tag_simi);
//		}
//		Iterator iterator = map.keySet().iterator();
//		while (iterator.hasNext()) {
//			researcherlist.add(map.get(iterator.next()));
//		}
		
		return SUCCESS;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ArrayList<ResearcherModel> getResearcherlist() {
		return researcherlist;
	}

	public void setResearcherlist(ArrayList<ResearcherModel> researcherlist) {
		this.researcherlist = researcherlist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
