package com.vicchan.scsample.svc.demo.log.web;

import com.vicchan.scsample.svc.core.swagger.model.ApiJsonObject;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonProperty;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonResult;
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

import static com.vicchan.scsample.svc.core.common.GlobalString.*;

@RestController
@RequestMapping("/auth")
@Validated
@Api("日志API")
public class LogAuthController {
  private final Log logger = LogFactory.getLog( LogAuthController.class );

  @Autowired
  private LogService logService;

  @ApiOperation(value="登录失败日志", notes = "写入登录失败的日志")
  @ApiJsonObject(
      name = "logAuthFail",
      value={
          @ApiJsonProperty( name = JSON_ACTION,required = true),
          @ApiJsonProperty( name = JSON_ERRMSG,required = true, example =  "用户名密码错误"),
          @ApiJsonProperty( name = JSON_USERNAME,required = true)
      },
      result = @ApiJsonResult(value = {JSON_ERRNO,JSON_ERRMSG})
  )
  @ApiImplicitParam(name = "body",required = true,dataType = "logAuthFail")
  @ApiResponses({
      @ApiResponse( code=200,message="OK",reference = "logAuthFail")
  })
  @PostMapping("/fail")
  public Object fail(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, JSON_ACTION );
    String errmsg = JacksonUtil.parseString( body, JSON_ERRMSG );
    String username = JacksonUtil.parseString( body, JSON_USERNAME );
    logger.info( "登录失败，写入日志" );
    return logService.logAuthFail( action, errmsg, username );
  }

  @ApiOperation(value="登录成功日志", notes = "写入登录成功的日志")
  @ApiJsonObject(
      name = "logAuthSucceed",
      value={
          @ApiJsonProperty( name = JSON_ACTION,required = true),
          @ApiJsonProperty( name = JSON_USERNAME,required = true)
      },
      result = @ApiJsonResult(value = {JSON_ERRNO,JSON_ERRMSG})
  )
  @ApiImplicitParam(name = "body",required = true,dataType = "logAuthSucceed")
  @ApiResponses({
      @ApiResponse( code=200,message="ok",reference = "logAuthSucceed")
  })
  @PostMapping("/succeed")
  public Object succeed(@RequestBody String body, HttpServletRequest request) {
    String action = JacksonUtil.parseString( body, JSON_ACTION );
    String username = JacksonUtil.parseString( body, JSON_USERNAME );
    logger.info( "登录成功，写入日志" );
    return logService.logAuthSucceed( action, username );
  }
}
