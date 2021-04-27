package com.wooridreamcardream.meaningout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MeaningoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeaningoutApplication.class, args);
	}

}
