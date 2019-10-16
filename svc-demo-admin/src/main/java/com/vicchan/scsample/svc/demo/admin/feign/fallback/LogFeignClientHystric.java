package com.vicchan.scsample.svc.demo.admin.feign.fallback;

import com.vicchan.scsample.svc.core.util.ResponseUtil;
import com.vicchan.scsample.svc.demo.admin.feign.LogFeignClient;
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
