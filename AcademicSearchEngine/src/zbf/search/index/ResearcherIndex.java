package zbf.search.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import zbf.search.model.ResearcherModel;
import zbf.search.util.StdOutUtil;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class ResearcherIndex {
	private String indexPath;
	private IKAnalyzer analyzer = new IKAnalyzer(true);

	public ResearcherIndex(String indexPath) {
		this.indexPath = indexPath;
	}

	public void build() throws IOException {
		FSDirectory directory = null;
		IndexWriterConfig conf = null;
		IndexWriter writer = null;
		directory = FSDirectory.open(new File(indexPath));
		conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
		writer = new IndexWriter(directory, conf);
		// data
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection coll = db.getCollection("researchers");
		DBCursor cursor = coll.find();
		try {
			while (cursor.hasNext()) {
				ResearcherModel model = new ResearcherModel();
				DBObject obj = cursor.next();
				model.setName((String) obj.get("name"));
				model.setWorkplace((String) obj.get("workplace"));
				model.setHomepage((String) obj.get("homepage"));
				
				BasicDBList list = (BasicDBList) obj.get("field");
				String s = "";
				for (int i = 0; i < list.size(); i ++) {
					if (list.get(i).toString().length() > 1) {
						String tmp = (String) list.get(i);
						tmp = tmp.replace("&amp;", "");
						if (s == "") {
							s = tmp;
						} else {
							s = s + ", " + tmp;
						}
					}
				}
				StdOutUtil.out(s);
				model.setField(s);
				
				Document doc = new Document();
				doc.add(new Field("name", model.getName(), Store.YES, Index.ANALYZED));
				doc.add(new Field("workplace", model.getWorkplace(), Store.YES, Index.ANALYZED));
				doc.add(new Field("homepage", model.getHomepage(), Store.YES, Index.NOT_ANALYZED));
				doc.add(new Field("field", model.getField(), Store.YES, Index.ANALYZED));
				writer.addDocument(doc);
			}
		} finally {
			cursor.close();
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		ResearcherIndex ri = new ResearcherIndex(
				"E://softs2/apache-solr-3.6.2/example/multicore/core0/data/index");
		ri.build();
	}
}
