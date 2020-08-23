package com.springcloudalibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloudalibaba.handler.OrderCustomerBlockHandler;
import com.springcloudalibaba.domain.CommonResult;
import com.springcloudalibaba.domain.Order;
import com.springcloudalibaba.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:21
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Value("${spring.cloud.sentinel.transport.dashboard}")
    private String sentinelDashBoard;

    @Value("${spring.cloud.sentinel.transport.port}")
    private String sentinelPort;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("create")
    //@SentinelResource(value = "createOrder", blockHandlerClass = OrderCustomerBlockHandler.class,
    //        blockHandler = "handleException")
    @SentinelResource(value = "createOrder", blockHandler = "handleException")
    public CommonResult create(Order order) {
        orderService.createOrder(order);
        return new CommonResult(200, "订单创建成功");
    }

    @GetMapping("test")
    //@SentinelResource(value = "createOrderTest", blockHandlerClass = OrderCustomerBlockHandler.class,
    //        blockHandler = "handleException")
    @SentinelResource(value = "createOrderTest", blockHandler = "handleException")
    public CommonResult test() {
        Map<String, String> map = new HashMap<>();
        map.put("sentinelAddr", sentinelDashBoard);
        map.put("sentinelPort", sentinelPort);
        return new CommonResult(200, "成功！", map);
    }


    public CommonResult handleException(BlockException exception) {
        //TODO 当出现了异常，利用redis通知开发人员处理异常
        final String key = "OrderCustomerBlockHandler handleException rpc error occurred";
        ExecutorService pool = Executors.newFixedThreadPool(5);
        String oldKey = (String) redisTemplate.opsForValue().get(key);

        pool.submit(() -> {
            if (StringUtils.isBlank(oldKey)) {
                //报错给开发人员发信息
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, exception.getClass().getCanonicalName() + "no available server, caused by: " + exception.getMessage());
                redisTemplate.expire(key, 20, TimeUnit.SECONDS);
            } else {
                log.info("已经发送短信，20秒内不能重复发送");
            }
        });
        pool.shutdown();
        return new CommonResult(444, "当前访问人数较多，请稍后再试！");
    }
}
