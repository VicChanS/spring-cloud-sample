package com.vicchan.scsample.svc.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SvcCoreApplication {

  private static final Log LOGGER = LogFactory.getLog( SvcCoreApplication.class );

  public static void main(String[] args) {
    SpringApplication.run( SvcCoreApplication.class, args );
  }


}
