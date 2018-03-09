package com.demo.statsservice.statsservice.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.statsservice.statsservice.models.Transaction;
import com.demo.statsservice.statsservice.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TransactionService service;
	
	@Test
	public void shouldReturn201Status_PostTransaction() throws JsonProcessingException, Exception {
		long timeInMillis = System.currentTimeMillis();
		final Transaction transaction = new Transaction(14.1, timeInMillis);
        when(service.add(Mockito.any(Transaction.class))).thenReturn(true);
        
        ObjectMapper mapper = new ObjectMapper();
        
        mvc.perform(
	        		post("/transactions")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(mapper.writeValueAsString(transaction)))
	                .andExpect(status().isCreated())
	                .andReturn();
	}
	
	@Test
	public void shouldReturn204Status_PostTransaction() throws JsonProcessingException, Exception {
		long timeInMillis = System.currentTimeMillis();
		final Transaction transaction = new Transaction(14.1, timeInMillis - 61000);
        when(service.add(Mockito.any(Transaction.class))).thenReturn(false);
        
        ObjectMapper mapper = new ObjectMapper();
        
        mvc.perform(
	        		post("/transactions")
	                .contentType(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .content(mapper.writeValueAsString(transaction)))
	                .andExpect(status().isNoContent())
	                .andExpect(content().string(""))
	                .andReturn();
	}
}
