package dcd.academic.recommend;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import dcd.academic.mongodb.MyMongoClient;
import dcd.academic.solrj.SolrjHelper;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

public class ResearcherWordTag {

	public static void main(String[] args) throws IOException {
		
	}
	
	public void calTagSimi() {
		
	}
	
	public void rankTagList() {
		
	}
	
	public void saveResearcherTag() throws IOException {
		MyMongoClient mongoClient = new MyMongoClient("researchers");
		DBCollection coll = mongoClient.getDBCollection();
		// tags : title (half done)
		// moretags : title+abs (later)
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
