package com.sayhello.say;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class SayApplication{
	public static void main(String[] args) {
		SpringApplication.run(SayApplication.class, args);
	}

}
