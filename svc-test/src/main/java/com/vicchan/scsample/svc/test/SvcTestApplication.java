package com.vicchan.scsample.svc.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.vicchan.scsample.svc.core","com.vicchan.scsample.svc.test"})
@EnableEurekaClient
@RestController
@EnableDiscoveryClient
@RefreshScope
@Api("测试2")
public class SvcTestApplication {

  private static final Log LOGGER = LogFactory.getLog( SvcTestApplication.class );
  // private static final Logger LOGGER = Logger.getLogger(SvcTestApplication.class.getName());


  public static void main(String[] args) {
    SpringApplication.run( SvcTestApplication.class, args );
  }

  @Value("${server.port}")
  String port;

  @Value("${foo}")
  String foo;

  @RequestMapping("/hi")
  public String home(@RequestParam(value = "name", defaultValue = "VicChan") String name) {
    String info = "hi " + name + " ,i am from port:" + port;
    LOGGER.info( info );
    return info;
  }

  @RequestMapping(value = "/foo")
  public String hi(){
    LOGGER.info( "foo........");
    return foo;
  }


  @ApiOperation(value = "计算+", notes = "加法")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "a", paramType = "path", value = "数字a", required = true, dataType = "Long"),
      @ApiImplicitParam(name = "b", paramType = "path", value = "数字b", required = true, dataType = "Long")
  })
  @GetMapping("/testAPI2")
  public String testAPI2(){
    LOGGER.info( "testAPI......." );
    return "testAPI";
  }

}
