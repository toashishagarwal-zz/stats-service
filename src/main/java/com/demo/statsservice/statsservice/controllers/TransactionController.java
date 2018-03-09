package com.demo.statsservice.statsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.statsservice.statsservice.models.Transaction;
import com.demo.statsservice.statsservice.services.TransactionService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService service;
	
	@PostMapping("/transactions")
	ResponseEntity<String> add(@RequestBody Transaction transaction){
		if(service.add(transaction))
			return new ResponseEntity<>(CREATED);
		return new ResponseEntity<>(NO_CONTENT);
	}
}
