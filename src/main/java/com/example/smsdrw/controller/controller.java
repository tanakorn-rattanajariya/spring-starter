package com.example.smsdrw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smsdrw.exception.ServiceException;
import com.example.smsdrw.service.service;


@RestController
public abstract class controller<T,P> {
	protected Logger log = LoggerFactory.getLogger(controller.class);
	@Autowired
	protected ServiceException exception;
	@Autowired
	protected service service;
	@GetMapping("/{id}")
	public abstract ResponseEntity<?> get(P id);
	@GetMapping()
	public abstract ResponseEntity<?> list();
	@PostMapping()
	public abstract ResponseEntity<?> post(T data);
	@PutMapping("/{id}")
	public abstract ResponseEntity<?> put(T data,P id);
	@DeleteMapping("/{id}")
	public abstract ResponseEntity<?> delete(P id);
	
}
