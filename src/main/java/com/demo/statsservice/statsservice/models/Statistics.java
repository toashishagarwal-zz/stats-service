package com.demo.statsservice.statsservice.models;

public class Statistics {
	private double sum;
	private double max;
	private double min;
	private long count;
	private double average;
	
	public Statistics(){}
	
	public Statistics(double sum, double max, double min, long count, double average) {
	    this.sum = sum;
	    this.max = max;
	    this.min = min;
	    this.count = count;
	    this.average = average;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	@Override
	public boolean equals(Object obj) {
		Statistics s = (Statistics)obj;
		return this.average == s.average 
				&& this.count == s.count
				&& this.max == s.max
				&& this.min == s.min 
				&& this.sum == s.sum;
	}
}
