package com.trustly.challenge.service;

import java.io.IOException;
import java.util.List;

import com.trustly.challenge.controller.dto.InfoDto;
import com.trustly.challenge.model.Path;

public interface GitHubService {
	String linkRepository(String user,String repository);
	Path findFiles(String linkRepository) throws IOException;
	List<InfoDto> infoFilesGroup(Path path, String linkRepository, String commit) throws IOException;
	String findCommit(String linkRepository) throws IOException;

}
