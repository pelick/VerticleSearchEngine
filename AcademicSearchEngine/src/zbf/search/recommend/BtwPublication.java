package zbf.search.recommend;

import java.io.IOException;
import java.util.ArrayList;

import zbf.search.mongodb.MyMongoClient;
import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BtwPublication {
	public static void main(String[] args) throws IOException{
		MyMongoClient mongoClient = new MyMongoClient("publications");
		DBCollection coll = mongoClient.getDBCollection();

		ArrayList<String> doc1 = new ArrayList<String>();
		ArrayList<String> doc2 = new ArrayList<String>();

		DBObject target = coll.findOne();
		StdOutUtil.out(target.get("title"));
		StdOutUtil.out(target.get("abstract"));
		doc1 = StringUtil.getTokens((String) target.get("abstract"));

		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String s = (String) obj.get("abstract");
			if (s.length() > 3) {
				doc2 = StringUtil.getTokens(s);
				double sim = CosineDis.getSimilarity(doc1, doc2);
				if (sim > 0.55 && sim != 1.0) {
					StdOutUtil.out(sim);
					StdOutUtil.out(s);
				}
			}
		}
	}
}
