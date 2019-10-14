package com.vicchan.svc.demo.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope
@MapperScan("com.vicchan.svc.demo.admin.mapper")
@EnableFeignClients
// @EnableTransactionManagement
public class SvcDemoAdminApplication {

  private static final Log LOGGER = LogFactory.getLog( SvcDemoAdminApplication.class );

  public static void main(String[] args) {
    SpringApplication.run( SvcDemoAdminApplication.class, args );
  }

}