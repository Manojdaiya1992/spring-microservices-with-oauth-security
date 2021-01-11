package com.example.ZuulProxyWithOauthSecurity.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Long> {
	
	User findByUserName(String userName) throws Exception;

}
