package br.ufrgs.foodbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FoodbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodbookApplication.class, args);
	}
}
