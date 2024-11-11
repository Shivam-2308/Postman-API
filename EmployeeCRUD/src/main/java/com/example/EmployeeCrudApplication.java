package com.example;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EntityScan(basePackages = "com.example.model")
public class EmployeeCrudApplication {
	public static void main(String[] args) {

		SpringApplication.run(EmployeeCrudApplication.class, args);
		System.out.println("Application started!!");
	}


	//Creating the bean of ModelMapper
	@Bean
	public ModelMapper getModelMapperInstance(){
			return new ModelMapper();
	}

}
