package com.sunyard.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableZuulProxy
@SpringBootApplication
@EnableFeignClients
@EnableOAuth2Sso
public class GatewayServiceApplication {

	public static void main(String[] args) {
	    SpringApplication.run(GatewayServiceApplication.class, args);
	}
}
