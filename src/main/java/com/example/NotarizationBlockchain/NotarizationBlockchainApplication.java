package com.example.NotarizationBlockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableSpringDataWebSupport
public class NotarizationBlockchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotarizationBlockchainApplication.class, args);
	}

}
