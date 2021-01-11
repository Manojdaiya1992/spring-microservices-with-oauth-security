package com.example.AuthenticateUser;

import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name = "zuul-proxy-server")
@FeignClient(name="zuul-proxy-oauth-server")
public interface FeignClientProxy {

}
