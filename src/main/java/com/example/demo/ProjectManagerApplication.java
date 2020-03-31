package com.example.demo;

import com.example.demo.security.jwt.JwtAuthTokenFilter;
import com.example.demo.security.jwt.JwtProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectManagerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ProjectManagerApplication.class, args);

		JwtProvider jwtProvider = context.getBean(JwtProvider.class);

		System.out.println("Instance: " + jwtProvider);
		jwtProvider.wear();

		JwtAuthTokenFilter tokenFilter = context.getBean(JwtAuthTokenFilter.class);
		System.out.println("JwtAuthTokenFilter Instance: " + tokenFilter);
		System.out.println("Instance: " + jwtProvider);
		tokenFilter.tokenProvider.wear();
	}
}
