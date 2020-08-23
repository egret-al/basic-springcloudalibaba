package com.springcloudalibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloudalibaba.domain.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/14 18:29
 */
@Slf4j
public class AccountCustomerBlockHandler {

    @Resource
    private static RedisTemplate redisTemplate;

    public static CommonResult handleException(BlockException e) {
        final String key = "AccountCustomerBlockHandler handleException rpc error occurred";
        ExecutorService pool = Executors.newFixedThreadPool(5);
        String oldKey = (String) redisTemplate.opsForValue().get(key);

        pool.submit(() -> {
            if (StringUtils.isBlank(oldKey)) {
                //报错给开发人员发信息
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, e.getMessage());
                redisTemplate.expire(key, 20, TimeUnit.SECONDS);
            } else {
                log.info("已经发送短信，20秒内不能重复发送");
            }
        });
        pool.shutdown();
        return new CommonResult(444, "当前访问人数较多，请稍后再试！");
    }
}
