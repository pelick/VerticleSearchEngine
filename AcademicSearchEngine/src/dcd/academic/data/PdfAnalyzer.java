package dcd.academic.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.xml.sax.SAXException;

import dcd.academic.model.PaperModel;

public class PdfAnalyzer {
	public PaperModel extractPaperModel(InputStream stream) throws SAXException, TikaException {
		PaperModel pm = null;
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
			BufferedReader reader = new BufferedReader(new Tika().parse(stream,
					meta), 1024 * 2);
			String lineString = null;

			while ((lineString = reader.readLine()) != null) {
				if (lineString.contains("Abstract")) {
					isAbstr = true;
				} else if (lineString.contains("Introduction")) {
					isIntro = true;
				} else if (lineString.contains("Conclusions") || lineString.contains("Conclusion")) {
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
			pm = new PaperModel();
			pm.setHead(head);
			pm.setAbstrct(abstr);
			pm.setContent(content);
			pm.setConclu(conclu);
			pm.setRefers(refer);
			return pm;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Not full pdf");
			return null;
		}
	
	}
	
	public static void main(String[] args) {
		
	}
}
