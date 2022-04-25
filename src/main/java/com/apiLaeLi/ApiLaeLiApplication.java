package com.apiLaeLi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiLaeLiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiLaeLiApplication.class, args);		
		
		//System.out.print(new BCryptPasswordEncoder().encode("123"));
	}

}
