package com.example.smsdrw.controller;

import java.util.HashMap;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smsdrw.model.Account;

@RestController
@RequestMapping("/account")
public class AccountController extends controller<Account,String> {
	private BCryptPasswordEncoder bcrypt;
	@Override
	public ResponseEntity<?> list() {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.account().list());
	}

	@Override
	public ResponseEntity<?> post(@RequestBody Account data) {
		// TODO Auto-generated method stub
		data.setPassword(bcrypt.encode(data.getPassword()));
		return ResponseEntity.ok(service.account().post(data));
	}

	@Override
	public ResponseEntity<?> get(@RequestParam String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.account().get(id));
	}

	@Override
	public ResponseEntity<?> put(Account data, String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.account().put(data,id));
	}
	
	@Override
	public ResponseEntity<?> delete(String id) {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(service.account().delete(id));
	}
	
}
