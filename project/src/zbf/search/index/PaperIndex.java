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

import zbf.search.model.PaperModel;
import zbf.search.model.PublicationModel;
import zbf.search.util.StdOutUtil;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class PaperIndex {
	private String indexPath;
	private IKAnalyzer analyzer = new IKAnalyzer(true);

	public PaperIndex(String indexPath) {
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
		DB db = mongoClient.getDB("pdf");
		DBCollection coll = db.getCollection("fs");
		DBCursor cursor = coll.find();
		int i = 0;
		try {
			while (cursor.hasNext()) {

				
//				Document doc = new Document();
//				doc.add(new Field("title", model.getTitle(), Store.YES, Index.ANALYZED));
//				doc.add(new Field("pub_abstract", model.getPub_abstract(), Store.YES, Index.ANALYZED));
//				doc.add(new Field("conference", model.getConference(), Store.YES, Index.ANALYZED));
//				doc.add(new Field("view_url", model.getView_url(), Store.YES, Index.ANALYZED));
//				doc.add(new Field("author", model.getAuthor(), Store.YES, Index.ANALYZED));
//				writer.addDocument(doc);
//				StdOutUtil.out(model.getTitle());
			}
		} finally {
			cursor.close();
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		PublicationIndex pi = new PublicationIndex("E://softs2/apache-solr-3.6.2/example/multicore/core2/data/index");
		pi.build();
	}
}