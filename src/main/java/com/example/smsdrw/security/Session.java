package com.example.smsdrw.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.smsdrw.model.Account;
import com.example.smsdrw.model.LogInfo;
import com.example.smsdrw.service.AccountService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Component
public class Session {
	@Autowired
	HttpServletRequest request;
	@Autowired
	private AccountService accountService;
	private Account acc;
	private void setAccount(Account account) {
		this.acc = account;
	}
	
	public LogInfo sessionLog() {
		try {
			LogInfo logInfo = new LogInfo().builder()
					.ip(this.request.getRemoteAddr())
					.hostname(this.request.getRemoteHost())
					.method(this.request.getMethod())
					.serviceName(this.request.getRequestURI())
					.username(this.getUsername())
					.agent(this.request.getHeader("User-Agent"))
					.userHost(this.request.getHeader("Host"))
					.build();
			return logInfo;
		}catch(NullPointerException e) {
		}
		return null;
		
	}
	public Account getAccount() throws NullPointerException {
		this.claimAccount();
		return this.acc;
	}
	
	private String getUsername() {
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer ")) {
			this.setAccount(null);
			return null;
		}
		String token = header.replace("Bearer ", "");
		Claims claims = Jwts.parser()
				.setSigningKey("JwtSecretKey".getBytes())
				.parseClaimsJws(token)
				.getBody();
		String username = claims.getSubject();
		return username;
	}
	private void claimAccount() {
		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer ")) {
			this.setAccount(null);
			return;
		}
		String token = header.replace("Bearer ", "");
		Claims claims = Jwts.parser()
				.setSigningKey("JwtSecretKey".getBytes())
				.parseClaimsJws(token)
				.getBody();
		String username = claims.getSubject();
		Account tmp = accountService.findByUsername(username);
		if (tmp != null) {
			this.setAccount(tmp);
		}
	}
}
