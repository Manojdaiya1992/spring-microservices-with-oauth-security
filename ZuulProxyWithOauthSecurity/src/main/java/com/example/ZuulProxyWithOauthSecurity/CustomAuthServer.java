package com.example.ZuulProxyWithOauthSecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableAuthorizationServer
@Order(1)
public class CustomAuthServer extends AuthorizationServerConfigurerAdapter{
	
	private final String clientId="$2y$12$eUovJt3tV2DxfQEspaPxcucmYcPYPBGGojGoiI1vt4Oic3hfm62w6";
	private final String client_secret="$2y$12$QEK/vqmxWmO3ANBpH/5IQ.b1piR3dlk/NPn/ECvdiElK/yvOzStyq";
	
	@Autowired
	public AuthenticationManager authManager;
	
	private CustomUserDetailService userDetailService;
	
	public CustomAuthServer(CustomUserDetailService customUserDetailService) {
		//this.authManager=authenticationManager;
		this.userDetailService=customUserDetailService;
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	endpoints.tokenStore(tokenStore())
    .accessTokenConverter(accessTokenConverter()).authenticationManager(this.authManager).userDetailsService(userDetailService)
    .exceptionTranslator(loggingExceptionTranslator());
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		   security
           .tokenKeyAccess("permitAll()")
           .checkTokenAccess("isAuthenticated()");
		
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		clients
        .inMemory()
        .withClient(clientId).secret("{noop}"+client_secret)
        .authorizedGrantTypes("password", "authorization_code", "refresh_token")
       // .authorities("READ_ONLY_CLIENT")
        .scopes("read","write","all")
       // .resourceIds("oauth2-resource")
      //  .redirectUris("http://localhost:8081/login")
        .accessTokenValiditySeconds(60*60*24)
        .refreshTokenValiditySeconds(60*60*24*30);
	}
	
	  @Bean
	    public TokenStore tokenStore() {
	        return new JwtTokenStore(accessTokenConverter2());
	    }
	 
	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	      converter.setSigningKey("GZWoJMXR4DQCm8flOvD44TOgnDvbdGhxRdtw0jG0Y4n2hDSzteagvesHGzDQiFf");
	      converter.setVerifierKey("GZWoJMXR4DQCm8flOvD44TOgnDvbdGhxRdtw0jG0Y4n2hDSzteagvesHGzDQiFf");
	        return converter;
	    }
	   
	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter2() {
	        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
	        jwtAccessTokenConverter.setSigningKey("GZWoJMXR4DQCm8flOvD44TOgnDvbdGhxRdtw0jG0Y4n2hDSzteagvesHGzDQiFf");
	         DefaultAccessTokenConverter accessTokenConverter =(DefaultAccessTokenConverter) jwtAccessTokenConverter.getAccessTokenConverter();
	                accessTokenConverter.setUserTokenConverter(userAuthenticationConverter());
	                
	        return jwtAccessTokenConverter;  
	    }
	 
	    @Bean
	    public UserAuthenticationConverter userAuthenticationConverter() {
	        DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
	        defaultUserAuthenticationConverter.setUserDetailsService(userDetailService);
	        return defaultUserAuthenticationConverter;
	    }
	    
	    @Bean
	    @Primary
	    public DefaultTokenServices tokenServices() {
	        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	        defaultTokenServices.setTokenStore(tokenStore());
	        defaultTokenServices.setSupportRefreshToken(true);
	        return defaultTokenServices;
	    }

	    @Bean
	    public WebResponseExceptionTranslator loggingExceptionTranslator() {
	        return new DefaultWebResponseExceptionTranslator() {
	            @Override
	            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
	                e.printStackTrace();
	                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
	                HttpHeaders headers = new HttpHeaders();
	                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
	                OAuth2Exception excBody = responseEntity.getBody();
	                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
	            }
	        };
	    }
}
