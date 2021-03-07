package com.example.smsdrw.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.smsdrw.model.Account;
import com.example.smsdrw.repository.AccountRepository;

@Service
public class AccountService extends BaseService<Account,String, AccountRepository> {
	
	public Account findByUsername(String username) {
		return repository.findByUsername(username);
	}
	public Account findByPassword(String password) {
		return repository.findByPassword(password);
	}
}
