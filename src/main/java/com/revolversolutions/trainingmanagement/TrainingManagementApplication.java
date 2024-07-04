package com.revolversolutions.trainingmanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({
		"com.revolversolutions.trainingmanagement.mapper",
		"com.revolversolutions.trainingmanagement.serviceImpl",
		"com.revolversolutions.trainingmanagement.controller",
		"com.revolversolutions.trainingmanagement.exception",
		"com.revolversolutions.trainingmanagement.security",
		"com.revolversolutions.trainingmanagement.config",
        "com.revolversolutions.trainingmanagement.aspect"
})
@EnableJpaRepositories(basePackages = "com.revolversolutions.trainingmanagement.repository")
@EntityScan(basePackages = "com.revolversolutions.trainingmanagement.entity")
public class TrainingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingManagementApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
