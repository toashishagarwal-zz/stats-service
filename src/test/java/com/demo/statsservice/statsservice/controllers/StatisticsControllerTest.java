package com.demo.statsservice.statsservice.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.demo.statsservice.statsservice.models.Statistics;
import com.demo.statsservice.statsservice.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StatisticsController.class)
public class StatisticsControllerTest {
	
	@Autowired private MockMvc mvc;
	@Autowired private ObjectMapper mapper;
	
	@MockBean
	private TransactionService service;
	
	@Test
	public void shouldReturnStatistics() throws Exception {
		final Statistics s = new Statistics(10, 3, 4,5,2.5d);
		when(service.getStatistics()).thenReturn(s);
		
		final MvcResult result = mvc.perform(get("/statistics")
									.accept(MediaType.APPLICATION_JSON_UTF8))
									.andExpect(status().isOk())
									.andReturn();
		
		verify(service).getStatistics();
		assertEquals(s, mapper.readValue(result.getResponse().getContentAsString(), Statistics.class));
	}
}
