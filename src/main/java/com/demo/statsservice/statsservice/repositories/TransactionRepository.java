package com.demo.statsservice.statsservice.repositories;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.demo.statsservice.statsservice.models.Transaction;

@Repository
public class TransactionRepository {
	
	private ConcurrentNavigableMap<Long, List<Transaction>> transactions = new ConcurrentSkipListMap<Long, List<Transaction>>();
	private final Long TIME_INTERVAL=60l;

	public Boolean add(Transaction t) {
		if(isValid(t)) {
			addTransaction(t);
			return true;
		}
		return false;
	}

	private void addTransaction(Transaction t) {
		Instant instant = new Date(t.getTimestamp()).toInstant();
		final Long key = instant.getEpochSecond();
		List<Transaction> values = transactions.get(key);
		if(values == null)
			values = new ArrayList<Transaction>();
		values.add(t);
		transactions.put(key, values);
	}

	private boolean isValid(Transaction t) {
		Instant instant = new Date(t.getTimestamp()).toInstant();
		return Duration.between(instant, now()).getSeconds() <= TIME_INTERVAL;
	}

	public List<Transaction> getTransactions() {
		return transactions.tailMap(from())
							.values()
							.parallelStream()
							.flatMap(Collection::parallelStream)
							.collect(toList());
	}

	private Long from() {
		return now().minusSeconds(TIME_INTERVAL).getEpochSecond();
	}

	@Scheduled(fixedDelay=10*1000)
	void removeOldEntries(){
		int size = transactions.size();
		if(size > 0) {
			ConcurrentNavigableMap<Long, List<Transaction>> old = transactions.headMap(from());
			old.clear();
		}
	}
}
