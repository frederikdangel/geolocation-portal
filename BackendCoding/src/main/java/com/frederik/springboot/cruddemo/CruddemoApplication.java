package com.frederik.springboot.cruddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
public class CruddemoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure (SpringApplicationBuilder applicationBuilder) {
	    return applicationBuilder.sources(CruddemoApplication.class);
	}
}
