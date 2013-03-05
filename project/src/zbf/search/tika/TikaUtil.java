package zbf.search.tika;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import zbf.search.util.StdOutUtil;



public class TikaUtil {
	public static void main(String[] args) throws IOException, SAXException, TikaException {
		String path = "E:\\2.pdf";
		Parser parser = new PDFParser();
		
		InputStream iStream = new BufferedInputStream(new FileInputStream(new File(path)));
		ContentHandler iHandler = new BodyContentHandler(); 
		
		Metadata meta = new Metadata();
        meta.add(Metadata.CONTENT_ENCODING, "utf-8");
        parser.parse(iStream, iHandler, meta, new ParseContext());
        iHandler.startDocument();
        iHandler.endDocument();
        
        System.out.println(iHandler.toString());
	}
	
	public static String getPdfContent(String path) throws IOException, SAXException, TikaException {
		Parser parser = new PDFParser();
		
		InputStream iStream = new BufferedInputStream(new FileInputStream(new File(path)));
		ContentHandler iHandler = new BodyContentHandler(); 
		
		Metadata meta = new Metadata();
        meta.add(Metadata.CONTENT_ENCODING, "utf-8");
        parser.parse(iStream, iHandler, meta, new ParseContext());
        iHandler.startDocument();
        iHandler.endDocument();
        
        return iHandler.toString();
	}
}
