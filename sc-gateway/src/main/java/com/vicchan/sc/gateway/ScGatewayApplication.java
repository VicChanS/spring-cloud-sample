package com.vicchan.sc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run( ScGatewayApplication.class, args );
  }

}
