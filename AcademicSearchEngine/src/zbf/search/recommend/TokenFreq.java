package zbf.search.recommend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import zbf.search.tika.TikaUtil;
import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;

public class TokenFreq {
	
	private static final String PATH = "E://pdf/";
	
	public static void main(String[] args) throws SAXException, TikaException, IOException {
		File dir = new File(PATH);
		for (File file : dir.listFiles()) {
			String name = file.getName();
			String text = TikaUtil.getPdfContentByPath(PATH + name);
			StdOutUtil.out(PATH + name);
			
			ArrayList<String> list = StringUtil.getTokens(text);
			Map<String, Integer> tokenMap = new HashMap<String, Integer>();
			for (int i = 0; i < list.size(); i ++) {
				String token = list.get(i);
				if (tokenMap.containsKey(token)) {
					int v = tokenMap.get(token);
					v ++;
					tokenMap.remove(token);
					tokenMap.put(token, v);
				} else {
					tokenMap.put(token, 1);
				}
			}
			
			Map<Integer, String> valueMap = new TreeMap<Integer, String>();
			Iterator<String> iterator = tokenMap.keySet().iterator();
			while (iterator.hasNext()) {
				String s = iterator.next();
				Integer i = tokenMap.get(s);
				valueMap.put(i, s);
			}
			
			Iterator<Integer> iter = valueMap.keySet().iterator();
			while (iter.hasNext()) {
				Integer i = iter.next();
				String s = valueMap.get(i);
				StdOutUtil.out(i + " " + s);
			}
			
			break;
		}
		
	}
}
