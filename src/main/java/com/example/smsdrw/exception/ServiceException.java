package com.example.smsdrw.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceException {
	public ResponseEntity<?> forbidden() {
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You have no permission to call this service.");
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "You have no permission to call this service.");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
	}
	public ResponseEntity<?> invalid() {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
		HashMap<String, String> map = new HashMap<>();
		map.put("message", "Invalid input");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
}
