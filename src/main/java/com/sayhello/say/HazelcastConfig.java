package com.sayhello.say;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ManagedContext;
import com.hazelcast.eureka.one.EurekaOneDiscoveryStrategyFactory;
import com.hazelcast.instance.DefaultNodeContext;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.instance.NodeContext;
import com.hazelcast.spring.context.SpringManagedContext;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class HazelcastConfig implements ApplicationContextAware {

  private static Logger log = LoggerFactory.getLogger(HazelcastConfig.class);

  private ApplicationContext applicationContext;

  @Bean
  public ManagedContext managedContext() {
    ManagedContext springManagedContext = new SpringManagedContext();
    return springManagedContext;
  }

  @Bean
  public Config configLocal(ManagedContext managedContext, EurekaClient eurekaClient) {

    EurekaOneDiscoveryStrategyFactory.setEurekaClient(eurekaClient);
    Config config = new Config();


    config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    config.getNetworkConfig().getJoin().getEurekaConfig()
      .setEnabled(true)
      .setProperty("self-registration", "true")
      .setProperty("namespace", "hazelcast")
      .setProperty("use-metadata-for-host-and-port", "true");

    config.setManagedContext(managedContext);

    config.getManagementCenterConfig().setEnabled(true);
    config.getManagementCenterConfig().setUrl("http://hazelm:8080/hazelcast-mancenter");
    config.getManagementCenterConfig().setUpdateInterval(2);

    return config;



//    return hazelcastConfig;
  }

  @Bean
  public NodeContext localNodeContext() {
    NodeContext nodeContext = new DefaultNodeContext();
    return nodeContext;
  }

  @Bean
  public HazelcastInstance hazelcastInstance(Config config, NodeContext nodeContext) throws Exception {
    HazelcastInstance hazelcastInstance = HazelcastInstanceFactory.newHazelcastInstance(config, config.getInstanceName(), nodeContext);
    return hazelcastInstance;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @PreDestroy
  void destroyHazelcast() {
    Hazelcast.shutdownAll();
    HazelcastInstanceFactory.shutdownAll();
  }

}
