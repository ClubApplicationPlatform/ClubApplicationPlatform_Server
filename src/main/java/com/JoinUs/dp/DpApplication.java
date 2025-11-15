package com.JoinUs.dp;

// git 강의 sdf

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpApplication.class, args);
	}

}
