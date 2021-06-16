package com.bkbwongo.core.ebaasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
//@EnableAuthorizationServer
@EnableSwagger2
public class EbaasaApplication {
	public static void main(String[] args) {
		SpringApplication.run(EbaasaApplication.class, args);
	}
}
