package com.example.ZuulProxyWithOauthSecurity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="authenticatio-user",url="http://localhost:8100")
public interface AuthenticationFeign {
	
	@GetMapping("/hi")
	public Object callHiApi(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
