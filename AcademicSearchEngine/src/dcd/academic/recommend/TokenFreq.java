package dcd.academic.recommend;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import dcd.academic.mongodb.MyMongoClient;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

public class TokenFreq {

	public static void main(String[] args) throws UnknownHostException {
		MyMongoClient mongoClient = new MyMongoClient("publications");
		DBCollection coll = mongoClient.getDBCollection();
		
		DBCursor cursor = coll.find().skip(300).limit(10);

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			if (((String) obj.get("abstract")).length() > 20) {
				String text = (String) obj.get("title") + (String) obj.get("abstract");
				String title = (String) obj.get("title");

				StdOutUtil.out(text.length() + " length");

				try {
					ArrayList<String> list = StringUtil.getTokens(text);
					StdOutUtil.out(list.size() + " tokens");
					Map<String, Integer> tokenMap = new HashMap<String, Integer>();
					for (int i = 0; i < list.size(); i++) {
						String token = list.get(i);
						if (tokenMap.containsKey(token)) {
							int v = tokenMap.get(token);
							v++;
							tokenMap.remove(token);
							tokenMap.put(token, v);
						} else {
							tokenMap.put(token, 1);
						}
					}

					ArrayList<String> sort = new ArrayList<String>();
					Iterator<String> iterator = tokenMap.keySet().iterator();
					while (iterator.hasNext()) {
						String s = iterator.next();
						Integer i = tokenMap.get(s);
						sort.add(i + "$$" + s);
					}

					Collections.sort(sort);
					StdOutUtil.out(title);
					for (String s : sort) {
						StdOutUtil.out(s);
					}

					StdOutUtil.out("/////////////////////////////////////");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
