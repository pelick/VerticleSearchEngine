package dcd.academic.recommend;

import java.io.IOException;
import java.util.ArrayList;

import dcd.academic.mongodb.MyMongoClient;
import dcd.academic.util.StdOutUtil;
import dcd.academic.util.StringUtil;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 
 * @author pelick
 * 计算学者主页之间的内容相似度，后来没有使用，没有学者主页相关相似度需求和功能
 *
 */
public class BtwHomepage {
	public static void main(String[] args) throws IOException {
		MyMongoClient mongoClient = new MyMongoClient("homepages");
		DBCollection coll = mongoClient.getDBCollection();
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
