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
		config.getNetworkConfig().setPort(hazelcastPort);
		config.getProperties().setProperty("hazelcast.discovery.enabled", "true");

		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);

		EurekaOneDiscoveryStrategyFactory discoveryStrategyFactory = new EurekaOneDiscoveryStrategyFactory();
		Map<String, Comparable> properties = new HashMap<String, Comparable>();
		properties.put("self-registration", "true");
		properties.put("namespace", "hazelcast");
		DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(discoveryStrategyFactory, properties);
		joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

		return config;
	}

}
