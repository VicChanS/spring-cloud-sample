package com.vicchan.scsample.svc.demo.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope
@MapperScan("com.vicchan.scsample.svc.demo.admin.mapper")
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
public class SvcDemoAdminApplication {

  private static final Log LOGGER = LogFactory.getLog( SvcDemoAdminApplication.class );

  public static void main(String[] args) {
    SpringApplication.run( SvcDemoAdminApplication.class, args );
  }

}
