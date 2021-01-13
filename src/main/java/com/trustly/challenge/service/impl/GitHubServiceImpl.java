package com.trustly.challenge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trustly.challenge.service.GitHubService;
import com.trustly.challenge.config.WebScraping;
import com.trustly.challenge.controller.dto.InfoDto;
import com.trustly.challenge.controller.dto.InfoFindDto;
import com.trustly.challenge.model.Path;

@Service
public class GitHubServiceImpl implements GitHubService{

	@Override
	public Path findFiles(String linkRepository) throws IOException {
		String linkMaster = findLinkMaster(linkRepository);
		return getPathGitHub("https://github.com"+linkMaster);
		//
	}
	
	@Override
	@Cacheable(value = "infoFilesGroup", key="#commit")
	public List<InfoDto> infoFilesGroup(Path path,String linkRepository, String commit) throws IOException {

		List<InfoDto> infoList = new ArrayList<InfoDto>();
		

		
		for (String onePath : path.getPaths()) {
			

            String extension = onePath.substring(onePath.lastIndexOf(".") + 1);            
            int lines = findLines(linkRepository+onePath).getLines();
            double kb = findLines(linkRepository+onePath).getBytes();
            
            
            
            if (infoList.stream().filter(f -> f.getFileExtension().equals(extension)).findFirst().orElse(null) != null) {
            	
            	InfoDto infoTmp = infoList.stream().filter(f -> f.getFileExtension().equals(extension)).findFirst().get();
            	infoList.remove(infoTmp);
            	infoList.add(new InfoDto(extension,lines+infoTmp.getLines(),kb+infoTmp.getBytes()));
            }else {
            	
            	infoList.add(new InfoDto(extension,lines,kb));
            }
            
            

        }
		
		return infoList;
	}


	@Override
	public String linkRepository(String user,String repository) {
		return "https://github.com/"+user+"/"+repository;
	}
	

	private InfoFindDto findLines(String linkArchive) throws IOException {
		
		System.out.println(linkArchive);
		
		WebScraping webScraping = new WebScraping(linkArchive);
		
		List<String> tagInfoList = webScraping.findObjectPage("text-mono f6 flex-auto pr-3 flex-order-2 flex-md-order-1 mt-2 mt-md-0", "div");
		
		String tagInfo = tagInfoList.stream().collect(Collectors.joining(" "));
		
		
		return new InfoFindDto(lines(tagInfo),bytes(tagInfo));
		
	}
	
	@Override
	public String findCommit(String linkRepository) throws IOException {
		WebScraping webScraping = new WebScraping(linkRepository);
		
		//return webScraping.findValueObjectPage("js-details-container Details d-flex rounded-top-1 flex-items-center flex-wrap","src","div").stream().findFirst().get();
		return webScraping.findValueObjectPage("f6 link-gray text-mono ml-2 d-none d-lg-inline","href","a").stream().findFirst().get();
	}

	private String findLinkMaster(String linkRepository) throws IOException {
		
		WebScraping webScraping = new WebScraping(linkRepository);
		
		return webScraping.findValueObjectPage("js-tree-finder","data-url","fuzzy-list").stream().findFirst().get();
		
	}
	
	private int lines (String tagInfo) {
		int start = 0;
		
		int finish = tagInfo.indexOf("lines");
		
		if (finish >= 0) {
			for (int i = finish; i > 0; i--) {
				if (Character.isDigit(tagInfo.charAt(i-2))!=true)
		          {
		              start = i-1;
		              break;
		          }
			}
			
			String line= tagInfo.substring(start, finish);
			return Integer.parseInt(line.trim());
			
		}else {
			return 0;
		}
		
	}
	
	private double bytes (String tagInfo) {
		int start = 0;
		int finish = 0;
		
		finish = tagInfo.indexOf("Bytes");
		
		if (finish >= 0) {
			for (int i = finish; i > 0; i--) {
				if ((Character.isDigit(tagInfo.charAt(i-2))!=true) && (tagInfo.charAt(i-2)!='.'))
		          {
		              start = i-1;
		              break;
		          }
			}
			
			String line= tagInfo.substring(start, finish);
			return Double.parseDouble(line.trim());
			
		}else {
			finish = tagInfo.indexOf("KB");
			
			if (finish >= 0) {
				for (int i = finish; i > 0; i--) {
					if ((Character.isDigit(tagInfo.charAt(i-2))!=true) && (tagInfo.charAt(i-2)!='.'))
			          {
			              start = i-1;
			              break;
			          }
				}
				
				String line= tagInfo.substring(start, finish);
				return Double.parseDouble(line.trim())*1024.00;
				
			}else {
				finish = tagInfo.indexOf("MB");
				
				if (finish >= 0) {
					for (int i = finish; i > 0; i--) {
						if ((Character.isDigit(tagInfo.charAt(i-2))!=true) && (tagInfo.charAt(i-2)!='.'))
				          {
				              start = i-1;
				              break;
				          }
					}
					
					String line= tagInfo.substring(start, finish);
					return Double.parseDouble(line.trim())*1024.00*1024.00;
					
				}else {
					finish = tagInfo.indexOf("GB");
					
					if (finish >= 0) {
						for (int i = finish; i > 0; i--) {
							if ((Character.isDigit(tagInfo.charAt(i-2))!=true) && (tagInfo.charAt(i-2)!='.'))
					          {
					              start = i-1;
					              break;
					          }
						}
						
						String line= tagInfo.substring(start, finish);
						return Double.parseDouble(line.trim())*1024.00*1024.00*1024.00;
						
					}else {
						return 0;
					}
				}
			}
		}
	}


	private Path getPathGitHub(String Url) {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Path> response = restTemplate.getForEntity(Url,Path.class);
		
	    HttpStatus statusCode = response.getStatusCode();
	    if (statusCode == HttpStatus.OK) {	    	
	        return response.getBody();
	    }else {
	    	
	    	return null;
	    }
	}
}
