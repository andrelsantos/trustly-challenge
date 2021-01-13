package com.trustly.challenge.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebScraping {
	
	
	//Class that uses webscraping devices to search for items on the html page
	
	
	private String gitHubRepository;
	

	public WebScraping(String gitHubRepository) throws IOException {
		this.gitHubRepository = gitHubRepository;
	}

	//convert html page to string
	public String getHtml(){
		/*
		URL url = new URL(gitHubRepository);
		HttpURLConnection con = null;
		
		try {
			con = (HttpURLConnection) url.openConnection();
			if (con.getResponseCode() != 200) {
				throw new IOException("HTTP error code : "+ con.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			
			return br.lines().collect(Collectors.joining(" "));
		}finally {
			con.disconnect();
		}
		*/
		try {
			URL url = new URL(gitHubRepository);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			if (con.getResponseCode() != 200) {
				throw new IOException("HTTP error code : "+ con.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			
			return br.lines().collect(Collectors.joining(" "));
		}catch(IOException e) {
			//refatoring in case of page access error
			return "";
		}
		
		

		
	    
	}
	
	//find object page (class) 
	public List<String> findObjectPage(String object, String tag) throws IOException{
		String html = getHtml();
		
		//System.out.println(object + " / " + tag + " / " +html);
		
		String pattern = "<"+tag+".*?</"+tag+">";
		
		 // Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		
		// Now create matcher object.
		Matcher m = r.matcher(html);
		
		List<String> findObjectList = new ArrayList<String>();
		
		while (m.find()) {
			if (m.group().contains(object)) {
				findObjectList.add(m.group());
			}
        }

		return findObjectList;
	}
	
	//find object page (class) and value
	public List<String> findValueObjectPage(String object,String value, String tag) throws IOException{
		
		//List<String> objectPage = findObjectPage(object, tag).stream().filter(t -> t.contains(object)).collect(Collectors.toList());
		List<String> objectPage = findObjectPage(object, tag).stream().collect(Collectors.toList());
		
		List<String> objValueList = new ArrayList<String>();
		
		
		for(String obj : objectPage) {
	    	
	    	String objTmp = obj.substring(obj.indexOf(value+"=")+(value.length()+2),obj.length());
	    	
	    	objValueList.add(objTmp.substring(0,objTmp.indexOf('"')));
	    }
		
		return objValueList;
	}

}
