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
import org.xml.sax.SAXException;

import zbf.search.data.pdf.PdfAnalyzer;
import zbf.search.model.PaperModel;
import zbf.search.mongodb.MongoGridFS;
import zbf.search.mongodb.MyMongoClient;
import zbf.search.util.StdOutUtil;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;

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
		MyMongoClient client = new MyMongoClient("papers");
		DBCollection coll = client.getDBCollection();
		DBCursor cursor = coll.find();
		while(cursor.hasNext()) {
			DBObject obj = cursor.next();
			String name = (String) obj.get("name");
			String title = (String) obj.get("title");
			String url = (String) obj.get("url");
			String text = (String) obj.get("text");
			
			Document doc = new Document();
			doc.add(new Field("name", name, Store.YES, Index.ANALYZED));
			doc.add(new Field("title", title, Store.YES, Index.ANALYZED));
			doc.add(new Field("url", url, Store.YES, Index.NOT_ANALYZED));
			doc.add(new Field("text", text, Store.NO, Index.ANALYZED));

			writer.addDocument(doc);
			StdOutUtil.out(name);
		}

		writer.close();
	}

	public static void main(String[] args) throws IOException {
		PaperIndex pi = new PaperIndex("E://softs2/apache-solr-3.6.2/example/multicore/core2/data/index");
		pi.build();
	}
}