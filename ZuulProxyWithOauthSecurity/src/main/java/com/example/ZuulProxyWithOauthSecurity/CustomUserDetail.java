package com.example.ZuulProxyWithOauthSecurity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class CustomUserDetail extends UserDao implements UserDetails{
	
	UserDao userDao =null;
	 public CustomUserDetail(UserDao user) {
		 super(user);
		 this.userDao = user;
		System.out.println(user.getUserName()+"    "+user.getPassword()+"     "+user.isWorking());
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("Roles    "+userDao.getRoles().isEmpty());
		return super.getRoles();
	}

	@Override
	public String getPassword() {
		System.out.println("Password    "+userDao.getPassword());
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		try {
			return new ObjectMapper().writeValueAsString(userDao);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
	    System.out.println(userDao.isWorking());
		return super.isWorking();
	}
	
	public int getUserId() {
		return 1;
	}

}
