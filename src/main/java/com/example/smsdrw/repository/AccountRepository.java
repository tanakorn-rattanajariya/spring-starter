package com.example.smsdrw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.smsdrw.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
	public Account findByUsername(String username);
	public Account findByPassword(String password);
	public Account findByRef(String ref);
}
