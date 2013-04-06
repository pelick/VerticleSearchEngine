package zbf.search.recommend;

import java.net.UnknownHostException;

import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class BtwAuthor {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection coll = db.getCollection("researchers");
		DBCursor cursor = coll.find();
		
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String name = (String) obj.get("name");
			String workplace = (String) obj.get("workplace");
			BasicDBList fields = (BasicDBList) obj.get("field");
			
			StdOutUtil.out(name);
			StdOutUtil.out(workplace);
			StdOutUtil.out(StringUtil.filterAmp(fields.toString()));

		}
	}
}
