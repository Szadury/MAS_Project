package com.pjwstk.MAS.BeerBar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class BeerBarApplication {
	public static void main(String[] args) {
		SpringApplication.run(BeerBarApplication.class, args);
	}
}
