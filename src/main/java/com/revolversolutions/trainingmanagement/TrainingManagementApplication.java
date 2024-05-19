package com.revolversolutions.trainingmanagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan({
		"com.revolversolutions.trainingmanagement.mapper",
		"com.revolversolutions.trainingmanagement.serviceImpl",
		"com.revolversolutions.trainingmanagement.controller",
		"com.revolversolutions.trainingmanagement.exception",
		"com.revolversolutions.trainingmanagement.security"
})
@EntityScan("com.revolversolutions.trainingmanagement.entity")
@EnableJpaRepositories("com.revolversolutions.trainingmanagement.repository")
public class TrainingManagementApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		SpringApplication.run(TrainingManagementApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
