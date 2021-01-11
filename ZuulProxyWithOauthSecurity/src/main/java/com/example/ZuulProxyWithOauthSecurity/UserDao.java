package com.example.ZuulProxyWithOauthSecurity;

import java.io.Serializable;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UserDao implements Serializable {

	private static final long serialVersionUID = 8498970432947971015L;

    private String userName;
    
    private String password;
    
    private boolean isWorking;
    
    private Set<GrantedAuthority> roles;

	
	public UserDao(UserDao user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.isWorking = user.isWorking();
		this.roles = user.getRoles();
	}
	
}
