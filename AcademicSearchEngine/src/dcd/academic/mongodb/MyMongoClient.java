package dcd.academic.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import com.mongodb.MongoClient;

/**
 * 
 * @author pelick
 * mongodb client，方便其他模块使用mongodb
 *
 */
public class MyMongoClient {
	private String SERVER = "localhost";
	private int PORT = 30000;
	private String DBNAME = "academic";
	
	private String coll = "";
	
	private MongoClient mongoClient;
	
	
	public MyMongoClient(String coll) {
		this.coll = coll;
	}
	
	public MyMongoClient(String coll, String dbname) {
		this.coll = coll;
		this.DBNAME = dbname;
	}
	
	public synchronized DBCollection getDBCollection() throws UnknownHostException {
		mongoClient = new MongoClient(SERVER, PORT);
		DB mongodb = mongoClient.getDB(DBNAME);
		return mongodb.getCollection(coll);
	}
	
	public static void main(String[] args) {
		
	}
}
