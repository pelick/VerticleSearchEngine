package zbf.search.util;

import java.io.IOException;


public class ExecBatUtil {
	public static void execBat(String batpath) {
		Runtime runtime = Runtime.getRuntime();

		try {
			String[] args = new String[] { "cmd", "/c", "start", batpath };
			Process pro = runtime.exec(args);
			System.out.println(pro.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		execBat("E://runspider.bat");
	}
}
