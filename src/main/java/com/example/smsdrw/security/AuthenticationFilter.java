package com.example.smsdrw.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import com.example.smsdrw.model.Account;
import com.example.smsdrw.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;
	private AccountService accountService;
	public AuthenticationFilter(JwtConfig jwtConfig, AuthenticationManager authenticationManager,AccountService accountService) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.accountService = accountService;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
	}

	private static class ApplicationUser {
		private String username, password;
		private String ref;

//    	@SuppressWarnings("unused")
//		public ApplicationUser(String username,String password) {
//    		this.username = username;
//    		this.password = password;
//    	}
		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}
		
		public String getRef() {
			return ref;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public void setRef(String ref) {
			this.ref = ref;
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			
			ApplicationUser applicationUser = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
			
			String ref = applicationUser.getUsername();
			Account account = this.accountService.findByUsername(ref);
			/**
			 * @desc check Expired Timestamp
			 */
			if (account.getRefTimestamp().before(new Date())) {
				throw new BadCredentialsException("Your session has been expired");
			}
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					account.getUsername(), account.getUsername(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					null, null, new ArrayList<>()));
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		Long now = System.currentTimeMillis();
		log.info(new Date(now + jwtConfig.getExpiration() * 1000).toString());
		String token = Jwts.builder().setSubject(auth.getName())
				// Convert to list of strings.
				// This is important because it affects the way we get them back in the Gateway.
				.claim("authorities",
						auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwtConfig.getExpiration() * 1000)) // in																					// milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes()).compact();

		// Add token to header
//		Cookie cookie = new Cookie("token",token);
//		cookie.setHttpOnly(true);
//		cookie.setSecure(true);
//		cookie.setMaxAge(jwtConfig.getExpiration() * 1000);
//		response.addCookie(cookie);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
}
