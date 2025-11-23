package com.finance.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinanceDreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceDreamApplication.class, args);
	}

}
