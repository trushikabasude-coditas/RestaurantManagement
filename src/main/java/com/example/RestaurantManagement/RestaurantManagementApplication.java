package com.example.RestaurantManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RestaurantManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantManagementApplication.class, args);
	}

}
