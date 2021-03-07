package com.example.smsdrw.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smsdrw.model.Account;
import com.example.smsdrw.model.LogInfo;
import com.example.smsdrw.security.Session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
@Transactional(timeout=50)
public abstract class BaseService<T,P,R extends JpaRepository<T,P>> {
	@Autowired
	protected R repository;
	@Autowired
	HttpServletRequest request;
	@Autowired
	private AccountService accountService;
	@Autowired
	protected Session session;
	static final Logger log = LogManager.getLogger();
	
	
	
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	public Iterable<T> list(Integer... start) {
		Page<T> page = repository.findAll(
				  PageRequest.of(start == null || start.length == 0 ? 0 : start[0], 1000, Sort.by(Sort.Direction.ASC, "id")));
		return page;
	}
	public Optional<T> get(P id) {
		Optional<T> t = repository.findById(id);
		if (t.isPresent()) {
		return t;
		}
		throw new NoSuchElementException();
		
	}
	public T post(T data) {
		T t = repository.save(data);
		return t;
	}
	public T put(T data,P id) {
		Optional<T> dat = repository.findById(id);
		if (dat.isPresent()) {
			T t = dat.get();
			BeanUtils.copyProperties(data, t,getNullPropertyNames(data));
			return repository.save(t);
		}
		throw new NoSuchElementException();
	}
	public P delete(P id) {
		Optional<T> t = repository.findById(id);
		if (t.isPresent()) {
			repository.deleteById(id);
			return id;
		}
		throw new NoSuchElementException();
	}
}