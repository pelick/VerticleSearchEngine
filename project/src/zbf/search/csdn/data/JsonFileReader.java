package zbf.search.csdn.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import zbf.search.util.FileUtil;
import zbf.search.util.StdOutUtil;

public class JsonFileReader {
	
	public static final String FILENAME = "E://tutorial/article.json";
	
	public ArrayList<ArticleModel> getArticleModelData() {
		ArrayList<String> articleList = readFileByLines(FILENAME);
		ArrayList<ArticleModel> articleModelList = new ArrayList<ArticleModel>();
		
		for(String article : articleList) {
			StdOutUtil.out(article);
			//ArticleModel model = new ArticleModel();
			
			// filter and get the metadata
			
			//articleModelList.add(model);
		}
		
		return articleModelList;
	}
	
	private ArrayList<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				result.add(FileUtil.unicodeEsc2Unicode(tempString));
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		JsonFileReader reader = new JsonFileReader();
		reader.getArticleModelData();
	}
}
