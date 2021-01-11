package com.example.ZuulProxyWithOauthSecurity;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ZuulProxyWithOauthSecurity.model.User;
import com.example.ZuulProxyWithOauthSecurity.model.UserJpa;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	 //private static List<UserDao>  users = new ArrayList<>();
	
	@Autowired
	private UserJpa userJpa;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao userDao = null;
		try {
			  /*     users.clear();
			    //   new UserDao
			       users.add(new UserDao("Manoj", "dahiya", true, Collections.singleton(new SimpleGrantedAuthority("admin"))));
			       users.add(new UserDao("golu", "dahiya", true, Collections.singleton(new SimpleGrantedAuthority("user"))));
			       userDao = users.stream().filter(u-> u.getUserName().equalsIgnoreCase(username)).findFirst().get();*/
			       User user =  this.userJpa.findByUserName(username);
			       userDao = new UserDao(user.getUserName(), user.getPassword(), true, Collections.singleton(new SimpleGrantedAuthority("user")));
			   //    System.out.println("User Dao    "+userDao.isWorking());
		} catch (Exception e) {
		  e.printStackTrace();
		}
		if(userDao==null) {
			throw new UsernameNotFoundException("User Not Exist");
		}
		return new CustomUserDetail(userDao);
	}
}
