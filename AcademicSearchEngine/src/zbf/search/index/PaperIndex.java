package zbf.search.index;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;

import org.wltea.analyzer.lucene.IKAnalyzer;
import org.xml.sax.SAXException;

import zbf.search.data.pdf.PdfAnalyzer;
import zbf.search.model.PaperModel;
import zbf.search.mongodb.MongoGridFS;
import zbf.search.util.StdOutUtil;

import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;

public class PaperIndex {
	private String indexPath;
	private IKAnalyzer analyzer = new IKAnalyzer(true);

	public PaperIndex(String indexPath) {
		this.indexPath = indexPath;
	}

	public void build() throws IOException, MongoException,
			NoSuchAlgorithmException, SAXException, TikaException {
		FSDirectory directory = null;
		IndexWriterConfig conf = null;
		IndexWriter writer = null;
		directory = FSDirectory.open(new File(indexPath));
		conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
		writer = new IndexWriter(directory, conf);
		// data
		PdfAnalyzer analyzer = new PdfAnalyzer();
		File dir = new File("E://pdf/");
		for (File file : dir.listFiles()) {
			String path = file.getAbsolutePath();
			InputStream iStream = new BufferedInputStream(new FileInputStream(new File(path)));
			PaperModel model = analyzer.extractPaperModel(iStream);
			if (model != null) {
				Document doc = new Document();
				
//				model.setTitle(file.getName());
//				StdOutUtil.out("title--------------------------");
//				StdOutUtil.out(model.getTitle());
//				StdOutUtil.out("head--------------------------");
//				StdOutUtil.out(model.getHead());
//				StdOutUtil.out("abs--------------------------");
//				StdOutUtil.out(model.getAbstrct());
//				StdOutUtil.out("con--------------------------");
//				StdOutUtil.out(model.getContent());
//				StdOutUtil.out("clu--------------------------");
//				StdOutUtil.out(model.getConclu());
//				StdOutUtil.out("ref--------------------------");
//				StdOutUtil.out(model.getRefers());
			}
			
		}

		// 
		// doc.add(new Field("title", model.getTitle(), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("pub_abstract", model.getPub_abstract(), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("conference", model.getConference(), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("view_url", model.getView_url(), Store.YES,
		// Index.ANALYZED));
		// doc.add(new Field("author", model.getAuthor(), Store.YES,
		// Index.ANALYZED));
		// writer.addDocument(doc);
		// StdOutUtil.out(model.getTitle());

		writer.close();
	}

	public static void main(String[] args) throws IOException, MongoException,
			NoSuchAlgorithmException, SAXException, TikaException {
		PaperIndex pi = new PaperIndex(
				"E://softs2/apache-solr-3.6.2/example/multicore/core2/data/index");
		pi.build();
	}
}