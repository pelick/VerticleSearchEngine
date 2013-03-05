package zbf.search.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUrlUtil {
	public static void main(String[] args) throws Exception {
		download("http://keg.cs.tsinghua.edu.cn/jietang/publications/TKDD11-Tang-et-al-web-user-profiling.pdf", "E://a.pdf");
	}

	public static void download(String urlString, String filename)
			throws Exception {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		byte[] bs = new byte[1024];
		int len;
		OutputStream os = new FileOutputStream(filename);
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		os.close();
		is.close();
		
	}
}
