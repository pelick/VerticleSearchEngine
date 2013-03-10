package zbf.search.academic.publication;

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

import zbf.search.academic.publication.PublicationIndex;
import zbf.search.academic.publication.PublicationModel;
import zbf.search.util.StdOutUtil;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class PublicationIndex {
	private String indexPath;
	private IKAnalyzer analyzer = new IKAnalyzer(true);

	public PublicationIndex(String indexPath) {
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
		DBCollection coll = db.getCollection("publications");
		DBCursor cursor = coll.find();
		try {
			while (cursor.hasNext()) {
				PublicationModel model = new PublicationModel();
				DBObject obj = cursor.next();
				model.setTitle((String) obj.get("title"));
				model.setPub_abstract((String) obj.get("abstract"));
				model.setConference((String) obj.get("conference"));
				model.setView_url((String) obj.get("view_url"));
				
				BasicDBList list = (BasicDBList) obj.get("author");
				model.setAuthor(list.toString());
				
				Document doc = new Document();
				doc.add(new Field("title", model.getTitle(), Store.YES, Index.ANALYZED));
				doc.add(new Field("pub_abstract", model.getPub_abstract(), Store.YES, Index.ANALYZED));
				doc.add(new Field("conference", model.getConference(), Store.YES, Index.ANALYZED));
				doc.add(new Field("view_url", model.getView_url(), Store.YES, Index.ANALYZED));
				doc.add(new Field("author", model.getAuthor(), Store.YES, Index.ANALYZED));
				writer.addDocument(doc);
				StdOutUtil.out(model.getTitle());
			}
		} finally {
			cursor.close();
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		PublicationIndex pi = new PublicationIndex("E://softs2/apache-solr-3.6.2/example/multicore/core1/data/index");
		pi.build();
	}
}

