package com.vicchan.svc.demo.log.service;

import com.vicchan.svc.demo.log.dao.LogDAO;
import com.vicchan.svc.demo.log.pojo.LogDO;
import com.vicchan.svc.demo.log.util.IpUtil;
import com.vicchan.svc.demo.log.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 这里的日志类型设计成四种（当然开发者需要可以自己扩展）
 * 一般日志：用户觉得需要查看的一般操作日志，建议是默认的日志级别
 * 安全日志：用户安全相关的操作日志，例如登录、删除管理员
 * 订单日志：用户交易相关的操作日志，例如订单发货、退款
 * 其他日志：如果以上三种不合适，可以选择其他日志，建议是优先级最低的日志级别
 * <p>
 * 当然可能很多操作是不需要记录到数据库的，例如编辑商品、编辑广告品之类。
 */
@Service
public class LogService {
  public static final Integer LOG_TYPE_GENERAL = 0;
  public static final Integer LOG_TYPE_AUTH = 1;
  public static final Integer LOG_TYPE_ORDER = 2;
  public static final Integer LOG_TYPE_OTHER = 3;

  @Autowired
  private LogDAO logDAO;

  public Object logGeneralSucceed(String action, String username) {
    return logAdmin(LOG_TYPE_GENERAL, action, true, "", "", username );
  }

  public Object logGeneralSucceed(String action, String result, String username) {
    return logAdmin(LOG_TYPE_GENERAL, action, true, result, "", username );
  }

  public Object logGeneralFail(String action, String error, String username) {
    return logAdmin(LOG_TYPE_GENERAL, action, false, error, "", username );
  }

  public Object logAuthSucceed(String action, String username) {
    return logAdmin(LOG_TYPE_AUTH, action, true, "", "", username );
  }

  public Object logAuthSucceed(String action, String result, String username) {
    return logAdmin(LOG_TYPE_AUTH, action, true, result, "", username );
  }

  public Object logAuthFail(String action, String error, String username) {
    return logAdmin(LOG_TYPE_AUTH, action, false, error, "", username );
  }

  public Object logOrderSucceed(String action, String username) {
    return logAdmin(LOG_TYPE_ORDER, action, true, "", "", username );
  }

  public Object logOrderSucceed(String action, String result, String username) {
    return logAdmin(LOG_TYPE_ORDER, action, true, result, "", username );
  }

  public Object logOrderFail(String action, String error, String username) {
    return logAdmin(LOG_TYPE_ORDER, action, false, error, "", username );
  }

  public Object logOtherSucceed(String action, String username) {
    return logAdmin(LOG_TYPE_OTHER, action, true, "", "", username );
  }

  public Object logOtherSucceed(String action, String result, String username) {
    return logAdmin(LOG_TYPE_OTHER, action, true, result, "", username );
  }


  public Object logOtherFail(String action, String error, String username) {
    return logAdmin(LOG_TYPE_OTHER, action, false, error, "", username );
  }

  public Object logAdmin(Integer type, String action, Boolean succeed, String result, String comment, String username) {
    LogDO log = new LogDO();

    // Subject currentUser = SecurityUtils.getSubject();
    // if (currentUser != null) {
    //     LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
    //     if (admin != null) {
    //         log.setAdmin(admin.getUsername());
    //     } else {
    //         log.setAdmin("匿名用户");
    //     }
    // } else {
    //     log.setAdmin("匿名用户");
    // }

    if (username != null) {
      log.setAdmin( username );
    } else {
      log.setAdmin( "匿名用户" );
    }
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    if (request != null) {
      log.setIp( IpUtil.getIpAddr( request ) );
    }
    log.setType( type );
    log.setAction( action );
    log.setStatus( succeed );
    log.setResult( result );
    log.setComment( comment );
    logDAO.add( log );
    return ResponseUtil.ok();
  }

}
