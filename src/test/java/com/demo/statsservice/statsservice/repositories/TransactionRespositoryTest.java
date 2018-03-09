package com.demo.statsservice.statsservice.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.ReflectionUtils;

import com.demo.statsservice.statsservice.models.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRespositoryTest {

	@InjectMocks
	private TransactionRepository repository;
	
	@Test
	public void shouldSaveTraction() {
		long timemillis = System.currentTimeMillis();
		final Transaction transaction = new Transaction(5.3, timemillis);
		
		Boolean actual = repository.add(transaction);
		assertTrue(actual);
	}
	
	@Test
    public void shouldNotSaveTransactionIfInvalid() throws Exception {
		long nowMinus61sec = getMinusSeconds(61);
        final Transaction transaction = new Transaction(12.3, nowMinus61sec);

        final Boolean actual = repository.add(transaction);

        assertFalse(actual);
    }
	
	@Test
    public void shouldReturnTransactionsWithinConfiguredInterval() throws Exception {
		long nowMinus59sec = getMinusSeconds(59);
		long nowMinus60sec = getMinusSeconds(60);
        final Transaction t1 = new Transaction(1.5, nowMinus59sec);
        final Transaction t2 = new Transaction(3.5, nowMinus60sec);
        repository.add(t1);
        repository.add(t2);

        Thread.sleep(1000);
        final List<Transaction> transactions = repository.getTransactions();

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertTrue(transactions.contains(t1));
    }

	private long getMinusSeconds(int seconds) {
		return System.currentTimeMillis() - (seconds*1000);
	}
	
	@Test
    public void shouldRemoveTransactionsOlderThanConfiguredInterval() throws Exception {
        final Transaction t1 = new Transaction(1.2, getMinusSeconds(60));
        final Transaction t2 = new Transaction(2.4, getMinusSeconds(59));
        final Boolean isT1Added = repository.add(t1);
        final Boolean isT2Added = repository.add(t2);

        Thread.sleep(1000);
        repository.removeOldEntries();

        assertTrue(isT1Added);
        assertTrue(isT2Added);
        ConcurrentNavigableMap<Long, List<Transaction>> map = getTransactionsMapUsingReflection();
        assertNotNull(map);
        assertEquals(1, map.size());
        Instant t = new Date(t2.getTimestamp()).toInstant();
        assertTrue(map.containsKey(t.getEpochSecond()));
    }
	
	@SuppressWarnings("unchecked")
    private ConcurrentNavigableMap<Long, List<Transaction>> getTransactionsMapUsingReflection() {
        Field field = ReflectionUtils.findField(TransactionRepository.class, "transactions");
        if (field != null) {
            field.setAccessible(true);
            Object fieldValue = ReflectionUtils.getField(field, repository);
            return (ConcurrentNavigableMap<Long, List<Transaction>>) fieldValue;
        }
        return null;
    }
}
