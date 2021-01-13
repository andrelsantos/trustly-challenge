package com.trustly.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GitHubServiceTest {
	
	private MockMvc mockMvc;

    @Autowired
    private GitHubService service;

    
    
}
