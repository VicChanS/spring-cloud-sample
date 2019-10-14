package com.vicchan.svc.demo.admin.feign.fallback;

import com.vicchan.svc.demo.admin.feign.LogFeignClient;
import com.vicchan.svc.demo.admin.util.ResponseUtil;
import org.springframework.stereotype.Component;

/**
 * @author VicChan
 * @date 2019/10/14 5:28 PM
 */
@Component
public class LogFeignClientHystric implements LogFeignClient {
  @Override
  public Object authFail(String body) {
    return ResponseUtil.fail();
  }

  @Override
  public Object authSucceed(String body) {
    return ResponseUtil.fail();
  }
}
