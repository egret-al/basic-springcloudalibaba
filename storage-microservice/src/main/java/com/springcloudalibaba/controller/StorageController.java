package com.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.springcloudalibaba.domain.CommonResult;
import com.springcloudalibaba.handler.StorageCustomerBlockHandler;
import com.springcloudalibaba.service.StorageService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:29
 */
@RefreshScope
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    @RequestMapping("decrease")
    @SentinelResource(value = "decreaseAccount", blockHandlerClass = StorageCustomerBlockHandler.class,
            blockHandler = "handleException")
    public CommonResult decrease(Long productId, Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功！");
    }
}
