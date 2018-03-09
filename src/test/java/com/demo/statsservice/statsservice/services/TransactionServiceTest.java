package com.demo.statsservice.statsservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.demo.statsservice.statsservice.models.Statistics;
import com.demo.statsservice.statsservice.models.Transaction;
import com.demo.statsservice.statsservice.repositories.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	@InjectMocks
	private TransactionServiceImpl service;
	
	@Mock
	private TransactionRepository repository;
	
	@Test
	public void shouldAddTransaction(){
		final Transaction transaction = new Transaction(5.3, System.currentTimeMillis());
	    when(repository.add(transaction)).thenReturn(true);
	
	    final Boolean isAdded = service.add(transaction);
	
	    verify(repository).add(transaction);
	    assertTrue(isAdded);
	}
	
	@Test
	public void shouldReturnStatistics(){
		final Transaction transaction1 = new Transaction(2, System.currentTimeMillis());
		final Transaction transaction2 = new Transaction(3, System.currentTimeMillis());
	    when(repository.getTransactions()).thenReturn(Arrays.asList(transaction1, transaction2));
	    Statistics expected = new Statistics(5, 3, 2, 2, 2.5d);
	
	    final Statistics actual = service.getStatistics();
	
	    verify(repository).getTransactions();
	    assertEquals(expected, actual);
	}
	
}
