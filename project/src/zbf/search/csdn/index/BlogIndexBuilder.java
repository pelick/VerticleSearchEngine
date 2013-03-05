package zbf.search.csdn.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BlogIndexBuilder {
	private String indexPath;
	private IKAnalyzer analyzer = new IKAnalyzer(true);
	
	public BlogIndexBuilder(String indexPath) {
		this.indexPath = indexPath;
	}
	
	public void build() throws IOException {
		FSDirectory directory = null;
		IndexWriterConfig conf = null;
		IndexWriter writer = null;
		// data
		
		directory = FSDirectory.open(new File(indexPath));
		conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
		writer = new IndexWriter(directory, conf);
		
		// add docs
		
		writer.close();
	}
}
