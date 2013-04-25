package dcd.academic.recommend;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import dcd.academic.mongodb.MyMongoClient;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BtwPublication {
	
	public static final int NUM = 20;
	
	public static void main(String[] args) throws IOException{
		BtwPublication bp = new BtwPublication();
		//bp.updatePublicationForComma();
		PageRank pageRank = new PageRank(bp.getPagerankS("random"));
		pageRank.doPagerank();
	}
	
	public double getDist(String pub1, String pub2) throws IOException {
		if (pub1 != null && pub2 != null) {
			ArrayList<String> doc1 = StringUtil.getTokens(pub1);
			ArrayList<String> doc2 = StringUtil.getTokens(pub2);
			return CosineDis.getSimilarity(doc1, doc2);
		} else {
			return 0;
		}
	}
	
//	public List<Map<String, String>> getPubs(String name) {
//		
//	}
	
	public List<List<Double>> getPagerankS(String text) throws IOException {
		SolrjHelper helper = new SolrjHelper(1);
		List<String> pubs = helper.getPubsByTitle(text, 0, NUM);
		List<List<Double>> s = new ArrayList<List<Double>>();
		for (String pub : pubs) {
			List<Double> tmp_row = new ArrayList<Double>();
			double total = 0.0;
			for (String other : pubs) {
				if (!pub.equals(other)) {
					double tmp = getDist(pub, other);
					tmp_row.add(tmp);
					total += tmp;
				} else {
					tmp_row.add(0.0);
				}
			}
			s.add(getNormalizedRow(tmp_row, total));
		}
		return s;
	}
	
	public List<Double> getNormalizedRow(List<Double> row, double d) {
		List<Double> res = new ArrayList<Double>();
		for (int i = 0; i < row.size(); i ++) {
			res.add(row.get(i) / d);
		}
		StdOutUtil.out(res.toString());
		return res;
	}
	
	public void updatePublicationForComma() throws UnknownHostException {
		MyMongoClient client = new MyMongoClient("publications");
		DBCollection collection = client.getDBCollection();
		DBCursor cursor = collection.find(new BasicDBObject("author", new BasicDBObject("$regex", ".*,.*")));
		StdOutUtil.out(cursor.size());
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String title = (String) obj.get("title");
			String conference = (String) obj.get("conference");
			BasicDBList author = (BasicDBList) obj.get("author");
			String view_url = (String) obj.get("view_url");
			String abst = (String) obj.get("abstract");
			
			collection.remove(new BasicDBObject("title", title));
			StdOutUtil.out("[DEL]: " + author.toString());
			
			BasicDBList newauthor = new BasicDBList();
			for (int i = 0; i < author.size(); i ++) {
				String tmp = (String) author.get(i);
				tmp = tmp.replace(", ", "");
				tmp = tmp.replace(",", "");
				newauthor.add(tmp);
			}
			
			DBObject newobj = new BasicDBObject();
			newobj.put("title", title);
			newobj.put("conference", conference);
			newobj.put("author", newauthor);
			newobj.put("view_url", view_url);
			newobj.put("abstract", abst);
			collection.save(newobj);
			StdOutUtil.out("[ADD]: " + newauthor.toString());
		}
	}
}
