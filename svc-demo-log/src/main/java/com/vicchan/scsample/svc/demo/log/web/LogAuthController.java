package com.vicchan.scsample.svc.demo.log.web;

import com.vicchan.scsample.svc.core.util.JacksonUtil;
import com.vicchan.scsample.svc.demo.log.service.LogService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/auth")
@Validated
@Api("日志API")
public class LogAuthController {
  private final Log logger = LogFactory.getLog( LogAuthController.class );

  @Autowired
  private LogService logService;


  @ApiOperation(value="登录失败日志", notes = "写入登录失败的日志")
  @ApiResponses({
      @ApiResponse( code=0,message="成功")
  })
  // @ApiParam(name = "body", value = "JSON格式字符串，包括动作action、错误码error和用户名username",required = true,allowableValues = )
  @PostMapping("/fail")
  public Object fail(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, "action" );
    String error = JacksonUtil.parseString( body, "error" );
    String username = JacksonUtil.parseString( body, "username" );
    logger.info( "登录失败，写入日志" );
    return logService.logAuthFail( action, error, username );
  }

  @ApiOperation(value="登录成功日志", notes = "写入登录成功的日志")
  @ApiResponses({
      @ApiResponse( code=0,message="成功")
  })
  // @ApiParam(name = "body",value = "JSON格式字符串，包括动作action、和用户名username",required = true)
  @PostMapping("/succeed")
  public Object succeed(@RequestBody Map<String,Object> body, HttpServletRequest request) {
    // String action = JacksonUtil.parseString( body, "action" );
    // String username = JacksonUtil.parseString( body, "username" );
    String action = (String) body.get( "action" );
    String username = (String) body.get( "username" );
    logger.info( "登录成功，写入日志" );
    return logService.logAuthSucceed( action, username );
  }
}
