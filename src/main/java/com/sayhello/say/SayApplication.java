package com.sayhello.say;

import com.hazelcast.config.UserCodeDeploymentConfig;
import com.hazelcast.spring.context.SpringManagedContext;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import org.springframework.beans.factory.annotation.Value;
import com.hazelcast.core.ManagedContext;
//import com.hazelcast.core.;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

import static com.hazelcast.config.UserCodeDeploymentConfig.ClassCacheMode.ETERNAL;
import static com.hazelcast.config.UserCodeDeploymentConfig.ProviderMode.LOCAL_AND_CACHED_CLASSES;

@SpringBootApplication
public class SayApplication implements ApplicationContextAware {

	@Bean
	public ManagedContext managedContext() {
		ManagedContext springManagedContext = new SpringManagedContext();
		return springManagedContext;
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Value("${hazelcast.port:5701}")
	private int hazelcastPort;
	public static void main(String[] args) {
		SpringApplication.run(SayApplication.class, args);
	}

	@Bean
	public Config hazelcastConfig(ManagedContext managedContext) {
		Config config = new Config();

		config.getManagementCenterConfig().setEnabled(true);
		config.getManagementCenterConfig().setUrl("http://hazelm:8080/hazelcast-mancenter");
		config.getManagementCenterConfig().setUpdateInterval(2);

		config.getNetworkConfig().setPort(hazelcastPort);
		config.getNetworkConfig().setPortAutoIncrement(true);
		config.getProperties().setProperty("hazelcast.discovery.enabled", "true");

		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);

		EurekaOneDiscoveryStrategyFactory discoveryStrategyFactory = new EurekaOneDiscoveryStrategyFactory();
		Map<String, Comparable> properties = new HashMap<String, Comparable>();
		properties.put("self-registration", "true");
		properties.put("namespace", "hazelcast");
		DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(discoveryStrategyFactory, properties);
		joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);



		config.setManagedContext(managedContext);

		UserCodeDeploymentConfig userCodeDeploymentConfig = new UserCodeDeploymentConfig();
		userCodeDeploymentConfig.setEnabled(true)
			.setClassCacheMode(UserCodeDeploymentConfig.ClassCacheMode.ETERNAL)
			.setProviderMode(LOCAL_AND_CACHED_CLASSES);

		config.setUserCodeDeploymentConfig(userCodeDeploymentConfig)

		return config;
	}

}
