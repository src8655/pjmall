package com.example.pjmall.backend.controller.test;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilters(springSecurityFilterChain)
				.build();
	}
	
	@Test
	public void test() {
		assertNotNull(mockMvc);
		
	}
	
	
	@Test
	public void testHelloUnauthorized() throws Exception {
		//getAccessToken("test", "1234");
		mockMvc
		.perform(get("/hello"))
		.andDo(print())
		.andExpect(status().isUnauthorized());
	}
	

	@Test
	public void testHelloAuthorized() throws Exception {
		String accessToken = getAccessToken("test", "1234");
		mockMvc
		.perform(get("/hello"))
		.andDo(print())
		.andExpect(status().isOk());
	}

	private String getAccessToken(String username, String password) throws Exception {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "password");
		params.add("client_id", "pjmall");
		params.add("username", username);
		params.add("password", password);
		params.add("scope", "read");
		
		mockMvc
		.perform(post("/oauth/token")
		.params(params)
		.with(httpBasic("pjmall", "1234"))
		.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
		
		return "";
	}
}
