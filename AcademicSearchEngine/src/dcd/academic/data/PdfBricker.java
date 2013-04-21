package dcd.academic.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;

import dcd.academic.mongodb.MongoGridFS;
import dcd.academic.util.TikaUtil;
import dcd.academic.util.StdOutUtil;

public class PdfBricker {

	public void putPdfIntoMongo() throws UnknownHostException, MongoException,
			NoSuchAlgorithmException, FileNotFoundException {
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

	public void putFullTextIntoMongo() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection papers = db.getCollection("papers");
		DBCollection pubs = db.getCollection("publications");

		String dir = "E://pdf/";
		File[] files = new File(dir).listFiles();
		int i = 0;
		int jump = 7130;

		for (File file : files) {
			i++;
			if (i > jump) {
				String name = file.getName();
				String text = "";
				String title = "";
				String url = "";

				DBObject query = new BasicDBObject();
				try {
					query.put("view_url", endWithStr("/" + name));

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				DBCursor cursor = pubs.find(query);
				if (cursor.count() == 1) {
					while (cursor.hasNext()) {
						DBObject obj = cursor.next();
						title = (String) obj.get("title");
						url = (String) obj.get("view_url");
					}

					DBObject tmp = new BasicDBObject();
					tmp.put("title", title);
					if (papers.find(tmp).count() == 0) {
						try {
							text = TikaUtil.getPdfContentByPath(file.getPath());
						} catch (Exception e) {
							e.printStackTrace();
						}
						BasicDBObject document = new BasicDBObject();
						document.put("name", name);
						document.put("text", text);
						document.put("title", title);
						document.put("url", url);
						papers.insert(document);
						StdOutUtil.out("[insert] " + url);
					} else {
						StdOutUtil.out("[redundent] " + name);
					}
				}
			}
		}
	}

	private BasicDBObject endWithStr(String findStr) {
		Pattern pattern = Pattern.compile(findStr + "$", Pattern.MULTILINE);
		return new BasicDBObject("$regex", pattern);
	}

	private BasicDBObject getLikeStr(String findStr) {
		Pattern pattern = Pattern.compile("^.*" + findStr + ".*$",
				Pattern.CASE_INSENSITIVE);
		return new BasicDBObject("$regex", pattern);
	}

	public static void main(String[] args) throws UnknownHostException,
			MongoException, NoSuchAlgorithmException, FileNotFoundException,
			SAXException, TikaException {
		PdfBricker bricker = new PdfBricker();
		bricker.putFullTextIntoMongo();
	}

}
