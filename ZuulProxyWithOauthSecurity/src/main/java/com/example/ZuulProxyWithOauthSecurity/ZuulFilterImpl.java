package com.example.ZuulProxyWithOauthSecurity;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulFilterImpl extends ZuulFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(ZuulFilterImpl.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		System.out.println("Hello");
		RequestContext context = 
				RequestContext.getCurrentContext();
		
		HttpServletRequest request = context.getRequest();
		logger.info("request -> {} request uri -> {}", 
				request, request.getRequestURI());
		return null;
	}

	@Override
	public String filterType() {
		
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	

}
