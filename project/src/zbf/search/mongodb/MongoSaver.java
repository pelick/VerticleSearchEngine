package zbf.search.mongodb;

import java.net.UnknownHostException;

import zbf.search.model.PaperModel;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoSaver {
	
	public void pdfToMongo(PaperModel paper) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection coll = db.getCollection("papers");
		// save paper into mongodb
	}
}
