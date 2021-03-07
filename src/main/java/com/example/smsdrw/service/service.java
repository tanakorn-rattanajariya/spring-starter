package com.example.smsdrw.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smsdrw.model.Account;
import com.example.smsdrw.model.LogInfo;
import com.example.smsdrw.security.Session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class service {
	
	@Autowired
	private ApplicationUserDetailsService userDetailService;
	public ApplicationUserDetailsService userDetail() {
		log.info(session.sessionLog().toString());
		return userDetailService;
	}
	@Autowired
	private AccountService accountService;
	@Autowired Session session;
	public AccountService account() {
		log.info(session.sessionLog().toString());
		return this.accountService;
	}
}
