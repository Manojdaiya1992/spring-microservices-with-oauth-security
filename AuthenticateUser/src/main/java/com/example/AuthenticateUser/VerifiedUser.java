package com.example.AuthenticateUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VerifiedUser {
	
	private String username;
	
    private String enabled;
    
}
