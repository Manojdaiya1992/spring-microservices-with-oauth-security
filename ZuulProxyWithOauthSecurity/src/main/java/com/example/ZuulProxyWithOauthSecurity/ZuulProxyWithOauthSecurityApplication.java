package com.example.ZuulProxyWithOauthSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.example.ZuulProxyWithOauthSecurity.model.User;
import com.example.ZuulProxyWithOauthSecurity.model.UserJpa;

import feign.Feign;

@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
public class ZuulProxyWithOauthSecurityApplication implements CommandLineRunner {
	
	@Autowired
	private UserJpa userJpa;

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyWithOauthSecurityApplication.class, args);
	}
	
	@Bean
	public Feign feign() {
		return Feign.builder().build();
	}
	
   @Override
   public void run(String... args) throws Exception {	
	   if(userJpa.count() == 0) {
	   User user = new User();
       user.setUserName("Manoj");
       user.setPassword("Dahiya");
       userJpa.save(user);
       }
   }

}
