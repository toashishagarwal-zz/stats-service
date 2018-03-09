package com.demo.statsservice.statsservice.services;

import java.util.DoubleSummaryStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.statsservice.statsservice.models.Statistics;
import com.demo.statsservice.statsservice.models.Transaction;
import com.demo.statsservice.statsservice.repositories.TransactionRepository;

import static java.util.stream.Collectors.summarizingDouble;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository repository;
	
	@Override
	public Boolean add(Transaction t) {
		return repository.add(t);
	}

	@Override
	public Statistics getStatistics() {
		final DoubleSummaryStatistics stats = repository.getTransactions()
														.parallelStream()
														.collect(summarizingDouble(Transaction::getAmount));
		return createStatistics(stats);
	}

	private Statistics createStatistics(final DoubleSummaryStatistics stats) {
		Statistics s = new Statistics();
		s.setAverage(stats.getAverage());
		s.setCount(stats.getCount());
		s.setMax(stats.getMax());
		s.setSum(stats.getSum());
		s.setMin(stats.getMin());
		
		return s;
	}
}
