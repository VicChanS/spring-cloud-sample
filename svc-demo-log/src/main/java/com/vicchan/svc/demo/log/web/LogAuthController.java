package com.vicchan.svc.demo.log.web;

import com.vicchan.svc.demo.log.service.LogService;
import com.vicchan.svc.demo.log.util.JacksonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
@Validated
public class LogAuthController {
  private final Log logger = LogFactory.getLog( LogAuthController.class );

  @Autowired
  private LogService logService;

  @PostMapping("/fail")
  public Object authFail(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, "action" );
    String error = JacksonUtil.parseString( body, "error" );
    String username = JacksonUtil.parseString( body, "username" );
    return logService.logAuthFail( action, error, username );
  }

  @PostMapping("/succeed")
  public Object authSucceed(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, "action" );
    String username = JacksonUtil.parseString( body, "username" );
    return logService.logAuthSucceed( action, username );
  }
}
