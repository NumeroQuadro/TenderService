package com.belunin.avito_test_task.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = {"com.belunin.avito_test_task.Repositories"})
@ComponentScan(basePackages = {"com.belunin.avito_test_task.restapi", "com.belunin.avito_test_task.Services", "com.belunin.avito_test_task.Controllers"})
@EntityScan(basePackages = {"com.belunin.avito_test_task.Models"})
public class RestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}
}
