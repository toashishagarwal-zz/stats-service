package com.demo.statsservice.statsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.statsservice.statsservice.models.Statistics;
import com.demo.statsservice.statsservice.services.TransactionService;

@RestController
public class StatisticsController {

	@Autowired private TransactionService service;
	
	@GetMapping("/statistics")
	Statistics getStatistics(){
		return service.getStatistics();
	}
}
