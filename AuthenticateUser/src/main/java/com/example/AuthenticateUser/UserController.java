package com.example.AuthenticateUser;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	
	
	@GetMapping(value ="/hi", produces = MediaType.APPLICATION_JSON_VALUE)
	public String callHello() {
	    String name = SecurityContextHolder.getContext().getAuthentication().getName();
		return name;
	}

}
