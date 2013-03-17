package zbf.search.tika;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaUtil {
	public static void main(String[] args) throws SAXException, TikaException {
		String path = "E://pdf/WS04-04-014.pdf";
		Metadata meta = new Metadata();
		meta.add(Metadata.CONTENT_ENCODING, "utf-8");
		
		boolean isAbstr = false;
		boolean isIntro = false;
		boolean isConcl = false;
		boolean isRefer = false;
		String head = "";
		String abstr = "";
		String content = "";
		String conclu = "";
		String refer = "";
		try {
			InputStream stream = TikaInputStream.get(new File(path), meta);
			BufferedReader reader = new BufferedReader(new Tika().parse(stream, meta), 1024 * 2);
			String lineString = null;

			while ((lineString = reader.readLine()) != null) {
				if (lineString.contains("Abstract")) {
					isAbstr = true;
				} else if (lineString.contains("Introduction")) {
					isIntro = true;
				} else if (lineString.contains("Conclusions")) {
					isConcl = true;
				} else if (lineString.contains("References")) {
					isRefer = true;
				}
				
				if (isRefer) {
					refer = refer + " " + lineString;
				} else if (isConcl && !isRefer) {
					conclu = conclu + " " + lineString;
				} else if (isIntro && !isConcl) {
					content = content + " " + lineString;
				} else if (isAbstr && !isIntro) {
					abstr = abstr + " " + lineString;
				} else if (!isAbstr) {
					head = head + " " + lineString;
				}
            }
		} catch (IOException e) {
			System.out.println("Not full pdf");
		}
		
		System.out.println("[head]");
		System.out.println(head);
		System.out.println("[abstr]");
		System.out.println(abstr);
		System.out.println("[content]");
		System.out.println(content);
		System.out.println("[conclu]");
		System.out.println(conclu);
		System.out.println("[refer]");
		System.out.println(refer);
	}

	public static String getPdfContentByPath(String path) throws SAXException,
			TikaException {
		Parser parser = new PDFParser();
		try {
			InputStream iStream = new BufferedInputStream(new FileInputStream(
					new File(path)));
			ContentHandler iHandler = new BodyContentHandler();

			Metadata meta = new Metadata();
			meta.add(Metadata.CONTENT_ENCODING, "utf-8");
			parser.parse(iStream, iHandler, meta, new ParseContext());
			iHandler.startDocument();
			iHandler.endDocument();

			return iHandler.toString();
		} catch (IOException e) {
			System.out.println("Not full pdf");
			return "";
		}
	}

	public static String getPdfContentByStream(InputStream is) throws SAXException,
			TikaException {
		Parser parser = new PDFParser();
		try {
			ContentHandler iHandler = new BodyContentHandler();

			Metadata meta = new Metadata();
			meta.add(Metadata.CONTENT_ENCODING, "utf-8");
			parser.parse(is, iHandler, meta, new ParseContext());
			iHandler.startDocument();
			iHandler.endDocument();

			return iHandler.toString();
		} catch (IOException e) {
			System.out.println("Not full pdf");
			return "";
		}
	}
}
