package com.trustly.challenge.config;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WebScrapingTest {

	private MockMvc mockMvc;

	@Test
	void case1() throws IOException{
		WebScraping webScraping = new WebScraping("https://www.google.com");
		
		String html = webScraping.getHtml();
		
		List<String> findOPList = webScraping.findObjectPage("google", "div");
		
		Assertions.assertFalse(html.length()==0);
		Assertions.assertFalse(findOPList.size()==0);
	}

}
