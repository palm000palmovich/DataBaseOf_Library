package com.example.Library_DB;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
public class LibraryDbApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryDbApplication.class, args);
	}
}
