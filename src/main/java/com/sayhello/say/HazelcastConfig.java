package com.sayhello.say;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

  @Bean
  public Config hazelcastConfig() {
    Config config = new Config();
    config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    config.getNetworkConfig().getJoin().getEurekaConfig()
      .setEnabled(true)
      .setProperty("self-registration", "true")
      .setProperty("namespace", "hazelcast");
    return config;
  }

}
