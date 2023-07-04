package com.sayhello.say;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class SayApplication{

	@Value("${hazelcast.port:5701}")
	private int hazelcastPort;
	public static void main(String[] args) {
		SpringApplication.run(SayApplication.class, args);
	}

	@Bean
	public Config hazelcastConfig() {
		Config config = new Config();
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getEurekaConfig().setEnabled(true)
			.setProperty("self-registration", "true")
			.setProperty("namespace", "hazelcast");

		return config;
	}

}
