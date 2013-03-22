package zbf.search.data.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;

import zbf.search.mongodb.MongoGridFS;
import zbf.search.tika.TikaUtil;
import zbf.search.util.StdOutUtil;

public class PdfBricker {
	
	public void putPdfIntoMongo() throws UnknownHostException, MongoException, NoSuchAlgorithmException, FileNotFoundException {
		MongoGridFS gridfs = new MongoGridFS();  
		String pareDir = "E://pdf/";
		File dir = new File(pareDir);
		for (File f : dir.listFiles()) {
			String name = f.getName();
			String fileName = pareDir + name; 
			GridFSDBFile gridFSDBFile = gridfs.getByFileName(name); 
			if (gridFSDBFile == null) {
				gridfs.save(new FileInputStream(fileName), name);
				StdOutUtil.out("[Finished] " + name);
			} else {
				StdOutUtil.out("[existed] " + name);
			}
		}
	}

	public void getPdfFromMongo() throws UnknownHostException, MongoException, NoSuchAlgorithmException, SAXException, TikaException {
		MongoGridFS gridfs = new MongoGridFS();
		ArrayList<GridFSDBFile> filelist = gridfs.getFileList();
		for (GridFSDBFile file : filelist) {
			String content = TikaUtil.getPdfContentByStream(file.getInputStream());
			
		}
	}
	
	
	
	public static void main(String[] args) throws UnknownHostException, MongoException, NoSuchAlgorithmException, FileNotFoundException {
		PdfBricker bricker = new PdfBricker();
		bricker.putPdfIntoMongo();
	}
	
}
