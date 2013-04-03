package zbf.struts.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import zbf.search.util.StdOutUtil;

import com.opensymphony.xwork2.ActionSupport;

public class authorAction extends ActionSupport {
	
	private ArrayList<String> list = new ArrayList<String>();
	
	public String execute() throws IOException {
		
		String solrHttp = "localhost:9080/solr/core0/select/?q=";
		String urlString = solrHttp + "name:tom" + "&start=0&rows=10&indent=on";
		
		HttpURLConnection urlConnection = null;
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection();
		InputStream in = urlConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
		String line = bufferedReader.readLine();
		
        while (line != null) {    
        	list.add(line);
        	StdOutUtil.out(line);
            line = bufferedReader.readLine();  
        }
		
		return SUCCESS;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

}
