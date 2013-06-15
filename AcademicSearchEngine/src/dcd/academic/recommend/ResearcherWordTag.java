package dcd.academic.recommend;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dcd.academic.DAO.DAOfactory;
import dcd.academic.DAO.SaveDAO;
import dcd.academic.mongodb.MyMongoClient;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

/**
 * 
 * @author pelick
 * 为每位学者计算得到了一个tag list，内容来自于发表论文的文本进行切词和过滤后的结果，效果并不好
 *
 */
public class ResearcherWordTag {

	public static void main(String[] args) throws IOException {
		ResearcherWordTag rwt = new ResearcherWordTag();
		rwt.calTagSimi();
	}
	
	public void calTagSimi() throws IOException {
		MyMongoClient mongoClient = new MyMongoClient("researchers");
		DBCollection coll = mongoClient.getDBCollection();
		DBCursor cursor1 = coll.find();
		
		DAOfactory factory = new DAOfactory();
		SaveDAO dao = factory.getSaveDAO();
		
		while (cursor1.hasNext()) {
			DBObject obj1 = cursor1.next();
			if (obj1.get("moretags") != null && obj1.get("moretags") != "") {
				String user1 = (String) obj1.get("name");
				String tag1 = (String) obj1.get("moretags");
				StdOutUtil.out("[user1]: "+user1);
				DBCursor cursor2 = coll.find();
				TreeMap<Double, String> map = new TreeMap<Double, String>();
				int i = 0;
				while (cursor2.hasNext()) {
					DBObject obj2 = cursor2.next();
					
					if (obj2.get("moretags") != null && obj2.get("moretags") != "") {
						String user2 = (String) obj2.get("name");
						String tag2 = (String) obj2.get("moretags");
						double simi = CosineDis.getSimilarity(StringUtil.getTokens(tag1), StringUtil.getTokens(tag2));
						
						if (simi > 0.1) {
							map.put(simi, user2);
							StdOutUtil.out("[user2]: "+user2 + " {simi}: " + simi);
							i ++;
						}
						
						//dao.saveUserSimi(user1, user2, simi);
					}
				}
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					StdOutUtil.out(it.next().toString());
				}
				break;
			}
		}
	}
	
	public void rankTagList() {
		
	}
	
	public void saveResearcherTag() throws IOException {
		MyMongoClient mongoClient = new MyMongoClient("researchers");
		DBCollection coll = mongoClient.getDBCollection();
		DBCursor cursor = coll.find(new BasicDBObject("moretags", null)); 
		StdOutUtil.out(cursor.count());
		SolrjHelper helper = new SolrjHelper(0);

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String name = (String) obj.get("name");
			String microurl = (String) obj.get("microurl");
			if (helper.existAuthor(name)) {
				List<String> textList = helper.getAuthorAbstraction(name, 0, 100);
				if (textList.size() > 0) {
					HashSet<String> res = StringUtil.removeDuplToSet(textList);
					StdOutUtil.out("[name]: " + name + " [length]: " + res.size());
					StdOutUtil.out(res.toString());
					
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("moretags", res.toString()));
					BasicDBObject searchQuery = new BasicDBObject().append("microurl", microurl);
					coll.update(searchQuery, newDocument);
					
				} else {
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("moretags", ""));
					BasicDBObject searchQuery = new BasicDBObject().append("microurl", microurl);
					coll.update(searchQuery, newDocument);
					StdOutUtil.out("[No For Now]");
				}
			}
		}
	}
}
