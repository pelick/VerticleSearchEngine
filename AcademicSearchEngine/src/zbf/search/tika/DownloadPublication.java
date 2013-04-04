package zbf.search.tika;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import zbf.search.util.StdOutUtil;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DownloadPublication extends Thread {

	public static final String PATH = "E://pdf/";
	//public static final int NUM = 32404; // 
	private int num; 
	private int start;
	
	public DownloadPublication(int num, int start) {
		this.num = num;
		this.start = start;
		StdOutUtil.out("Thread " + num + " `s start num is: " + start);
	}
	
	@Override
	public void run() {
		try {
			this.doDownload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doDownload() throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 30000);
		DB db = mongoClient.getDB("academic");
		DBCollection coll = db.getCollection("publications");
		DBCursor cursor = coll.find().skip(start);
		int i = start; 
		while (cursor.hasNext()) {
			
			DBObject obj = cursor.next();
			if (obj.get("view_url") != "") {
				String url = (String) obj.get("view_url");
				
				if (url.endsWith(".pdf")) {
					StdOutUtil.out(url);
					int pos = url.lastIndexOf("/");
					String filename = url.substring(pos + 1);
					String filepath = PATH + filename;
					File f = new File(filepath);
					if (!f.exists()) {
						download(url, filepath, i);
					} else {
						StdOutUtil.out(i + " From Thread:" + num + " [Exits] " + filepath);
					}
				}
			}
			i ++;
		}
		cursor.close();
	}

	private void download(String urlString, String filepath, int i) throws MalformedURLException {
		URL url = new URL(urlString);
		try {
			URLConnection con;
			
			con = url.openConnection();
			
			InputStream is = con.getInputStream();

			byte[] bs = new byte[1024];
			int len;
			OutputStream os = new FileOutputStream(filepath);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.close();
			is.close();
			StdOutUtil.out(i + " From Thread:" + num + " [Finished] " + filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			StdOutUtil.out(i + " From Thread:" + num + " [Failed] " + filepath);
		}
		
	}

	public static void main(String[] args) throws Exception {
		ArrayList<DownloadPublication> dppool = new ArrayList<DownloadPublication>();
		int base = 180000; // 180000, 190000, 200000
		int i = 3;
		while (i > 0) {
			dppool.add(new DownloadPublication(i, base+(i-1)*10000));
			i --;
		}
		for (DownloadPublication dp : dppool) {
			dp.start();
		}
	}
}
