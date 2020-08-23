package com.springcloudalibaba.service;

import com.springcloudalibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:11
 */
@FeignClient(value = "storage-service")
public interface StorageService {

    @PostMapping(value = "/api/v1/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
