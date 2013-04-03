package zbf.search.recommend;

import java.io.IOException;
import java.util.ArrayList;

import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Homepage {
	public static void main(String[] args) throws IOException {
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection coll = db.getCollection("homepages");
		DBCursor cursor = coll.find();
		
		ArrayList<String> doc1 = new ArrayList<String>();
		ArrayList<String> doc2 = new ArrayList<String>();
		
		while(cursor.hasNext()) {
			if (doc1.size() > 0 && doc2.size() > 0) {
				double dist = CosineDis.getSimilarity(doc1, doc2);
				StdOutUtil.out(dist);
				StdOutUtil.out("");
				doc1 = new ArrayList<String>();
				doc2 = new ArrayList<String>();
			}
			DBObject obj = cursor.next();
			String url = (String) obj.get("url");
			String text = (String) obj.get("text");
			if (!StringUtil.isBlank(text)) {
				if (doc1.size() == 0) {
					doc1 = StringUtil.getTokens(text);
					StdOutUtil.out(url);
					//StdOutUtil.out(doc1.toString());
				} else {
					doc2 = StringUtil.getTokens(text);
					StdOutUtil.out(url);
					//StdOutUtil.out(doc2.toString());
				}
			}
		}
	}
}
