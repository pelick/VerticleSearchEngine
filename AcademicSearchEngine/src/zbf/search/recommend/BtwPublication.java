package zbf.search.recommend;

import java.io.IOException;
import java.net.UnknownHostException;

import zbf.search.mongodb.MyMongoClient;
import zbf.search.util.StdOutUtil;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BtwPublication {
	public static void main(String[] args) throws IOException{
		BtwPublication bp = new BtwPublication();
		//bp.updatePublicationForComma();
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
