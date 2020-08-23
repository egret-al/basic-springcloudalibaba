package com.springcloudalibaba.service;

import com.springcloudalibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:10
 */
@FeignClient("account-service")
public interface AccountService {

    @PostMapping(value = "/api/v1/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
