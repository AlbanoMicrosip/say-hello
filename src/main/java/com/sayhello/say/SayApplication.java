package com.sayhello.say;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
public class SayApplication implements SaludoController{

	@Value("${spring.application.name}")
	private String appName;
	private static Logger log = LoggerFactory.getLogger(SayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SayApplication.class, args);
	}

	@GetMapping("/greeting")
	public String greet() {
		log.info("Access /greeting");

		List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
		Random rand = new Random();

		int randomNum = rand.nextInt(greetings.size());
		return greetings.get(randomNum);
	}

	@GetMapping("/")
	public String home() {
		log.info("Access /");
		return "Hi!";
	}

	@Override
	public String saludo() {
		log.info("Access /saludo");

		List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
		Random rand = new Random();

		int randomNum = rand.nextInt(greetings.size());
		return greetings.get(randomNum);
	}

}
