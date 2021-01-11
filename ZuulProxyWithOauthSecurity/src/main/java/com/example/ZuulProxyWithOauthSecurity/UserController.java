package com.example.ZuulProxyWithOauthSecurity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationFeign authFeignClient;
	
	@GetMapping(value="/hello")
	public ResponseEntity<Object> sayHello() throws JsonProcessingException{
		Object object =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		String principal = mapper.writeValueAsString(object);
		System.out.println(principal);
		return new ResponseEntity<>("{name : manoj}",HttpStatus.OK);
	}
	
	@GetMapping("/call/auth/api")
	public Object callAuthApi(HttpServletRequest request) {
		System.out.println(this.authFeignClient);
		return this.authFeignClient.callHiApi(request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION));
	}

}
