package com.trustly.challenge.controller;

import java.io.IOException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.trustly.challenge.controller.dto.InfoDto;
import com.trustly.challenge.exceptions.ResourceNotFound;
import com.trustly.challenge.model.Path;
import com.trustly.challenge.service.GitHubService;


@RestController
public class GithubInfoController {
	
	
	@Autowired
	private GitHubService gitHubService;
	
	@GetMapping("/api/infogithub/{user}/{repository}")
	 public List<InfoDto>  gitHubInfo(@PathVariable final String user, @PathVariable final String repository) {

		 try {
			
			 //mount the url
			String linkRepository =  gitHubService.linkRepository(user,repository);
			
			//Fetch the commit to save in cache
			String commit = gitHubService.findCommit(linkRepository);
			
			if(commit == null) {
				throw new ResourceNotFound(String.class,linkRepository);		
			}
			System.out.println(commit);
			
			//search in the url containing the list of files
			Path links = gitHubService.findFiles(linkRepository + "/find/master");
			
			//returns a list containing the totals of lines and bytes grouped by extension
			return gitHubService.infoFilesGroup(links,linkRepository + "/blob/master/", commit);
		} catch (IOException e) {
			System.out.println(e);
			throw new ResourceNotFound(InfoDto.class,e.getMessage());		
			}
	 }

}