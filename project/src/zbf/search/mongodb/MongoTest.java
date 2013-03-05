package zbf.search.mongodb;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBConnector;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoTest {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("article");
//		Set<String> colls = db.getCollectionNames();
//
//		for (String s : colls) {
//		    System.out.println(s);
//		}
		DBCollection coll = db.getCollection("posts");
		System.out.println(coll.getCount());
		//DBObject obj = coll.findOne();
		//System.out.println(obj.toString());
//		DBCursor cursor = coll.find();
//		int i = 0;
//		try {
//		   while(cursor.hasNext()) {
//			   i ++;
//		       System.out.println(cursor.next());
//		   }
//		} finally {
//		   cursor.close();
//		   System.out.println(i);
//		}
	}
}
