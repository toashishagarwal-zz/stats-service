package com.demo.statsservice.statsservice.services;

import com.demo.statsservice.statsservice.models.Statistics;
import com.demo.statsservice.statsservice.models.Transaction;

public interface TransactionService {
	public Boolean add(Transaction t);
	public Statistics getStatistics();
}
