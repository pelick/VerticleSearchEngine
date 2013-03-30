package zbf.search.recommend;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import zbf.search.util.StdOutUtil;

public class CosineDis {

	public static double getSimilarity(String doc1, String doc2) {

		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null
				&& doc2.trim().length() > 0) {

			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();

			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				int charIndex = getId(d1);
				int[] fq = AlgorithmMap.get(charIndex);
				if (fq != null && fq.length == 2) {
					fq[0]++;
				} else {
					fq = new int[2];
					fq[0] = 1;
					fq[1] = 0;
					AlgorithmMap.put(charIndex, fq);
				}

			}

			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				int charIndex = getId(d2);
				int[] fq = AlgorithmMap.get(charIndex);
				if (fq != null && fq.length == 2) {
					fq[1]++;
				} else {
					fq = new int[2];
					fq[0] = 0;
					fq[1] = 1;
					AlgorithmMap.put(charIndex, fq);
				}

			}

			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqdoc1 = 0;
			double sqdoc2 = 0;
			double denominator = 0;
			while (iterator.hasNext()) {
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0] * c[1];
				sqdoc1 += c[0] * c[0];
				sqdoc2 += c[1] * c[1];
			}

			return denominator / Math.sqrt(sqdoc1 * sqdoc2);
		} else {
			throw new NullPointerException(
					" the Document is null or have not chars!!");
		}
	}

	public static boolean isHanZi(char ch) {
		// 判断是否汉字
		return (ch >= 0x4E00 && ch <= 0x9FA5);
	}

	/**
	 * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
	 * 
	 * @param ch
	 *            输入的GB2312中文字符或者ASCII字符(128个)
	 * @return ch在GB2312中的位置，-1表示该字符不认识
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
				return -1;
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
			return (short) (b0 * 94 + b1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static short getId(char ch) {
		return (short) ch;
	}

	public static void main(String[] args) {
		StdOutUtil.out(getSimilarity("asdfg", "gfdsa"));
		
	}
}
