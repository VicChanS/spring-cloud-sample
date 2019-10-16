package com.vicchan.scsample.svc.demo.admin.feign;

import com.vicchan.scsample.svc.demo.admin.feign.fallback.LogFeignClientHystric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 微服务内部访问接口
 */
@FeignClient(value = "svc-demo-log",fallback = LogFeignClientHystric.class)
public interface LogFeignClient {
    @RequestMapping(value = "/auth/fail",method = RequestMethod.POST)
    Object authFail(@RequestBody String body);

    @RequestMapping(value = "/auth/succeed",method = RequestMethod.POST)
    Object authSucceed(@RequestBody String body);
}

