package com.vicchan.scsample.svc.demo.log.web;

import com.vicchan.scsample.svc.core.util.JacksonUtil;
import com.vicchan.scsample.svc.demo.log.service.LogService;
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
  public Object fail(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, "action" );
    String error = JacksonUtil.parseString( body, "error" );
    String username = JacksonUtil.parseString( body, "username" );
    logger.info( "登录失败，写入日志" );
    return logService.logAuthFail( action, error, username );
  }

  @PostMapping("/succeed")
  public Object succeed(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, "action" );
    String username = JacksonUtil.parseString( body, "username" );
    logger.info( "登录成功，写入日志" );
    return logService.logAuthSucceed( action, username );
  }
}
