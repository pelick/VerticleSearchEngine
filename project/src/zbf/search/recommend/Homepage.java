package zbf.search.recommend;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import zbf.search.util.StdOutUtil;

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
			if (text.length() > 10) {
				if (doc1.size() == 0) {
					doc1 = getTokens(text);
					StdOutUtil.out(url);
					//StdOutUtil.out(doc1.toString());
				} else {
					doc2 = getTokens(text);
					StdOutUtil.out(url);
					//StdOutUtil.out(doc2.toString());
				}
			}
		}
		
	}
	
	private static ArrayList<String> getTokens(String text) throws IOException {
		ArrayList<String> tokens = new ArrayList<String>();
		InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
		BufferedReader input = new BufferedReader(new InputStreamReader(is));

		// true为智能划词, false为最细粒度划词
		IKSegmenter segmenter = new IKSegmenter(input, true);
		Lexeme token = null;
		do {
			token = segmenter.next();
			if (token != null) 
				if (token.getLexemeText().length() > 2)
					tokens.add(token.getLexemeText());
		} while (token != null);
		return tokens;
	}
}
