package com.example.smsdrw.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.smsdrw.model.Account;
import com.example.smsdrw.security.AuthenticationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplicationUserDetailsService implements UserDetailsService {
	@Autowired
	private AccountService account;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	try {
    		Account applicationUser = account.findByUsername(username);
    		 if (applicationUser == null) {
    	            throw new UsernameNotFoundException(username);
    	        }
    	        return new User(applicationUser.getUsername(), applicationUser.getPassword(),Collections.emptyList());
    	}catch(Exception e) {
    		log.info("error");
    		log.info(e.getLocalizedMessage());
    	}
        return null;
    }
}
