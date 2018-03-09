package com.demo.statsservice.statsservice.models;

public class Transaction {
	private double amount;
	private long timestamp;
	
	public Transaction() {
	}

	public Transaction(double amount, long timestamp){
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
