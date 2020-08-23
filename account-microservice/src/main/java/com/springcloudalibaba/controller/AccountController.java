package com.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.springcloudalibaba.domain.CommonResult;
import com.springcloudalibaba.handler.AccountCustomerBlockHandler;
import com.springcloudalibaba.service.AccountService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 21:39
 */
@RefreshScope
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("decrease")
    @SentinelResource(value = "decreaseAccount", blockHandlerClass = AccountCustomerBlockHandler.class,
            blockHandler = "handleException")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountService.decrease(userId, money);
        return new CommonResult(200, "扣减账户余额成功！");
    }
}
