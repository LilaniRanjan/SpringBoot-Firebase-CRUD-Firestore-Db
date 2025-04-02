package com.example.FirebasePractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FirebasePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirebasePracticeApplication.class, args);
		System.out.println("Starting..");
	}
}
