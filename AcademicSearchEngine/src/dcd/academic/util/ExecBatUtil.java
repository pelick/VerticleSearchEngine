package dcd.academic.util;

import java.io.IOException;

/**
 * 
 * @author pelick
 * 想通过java控制windows的cmd来启动爬虫程序，尝试失败
 *
 */
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
