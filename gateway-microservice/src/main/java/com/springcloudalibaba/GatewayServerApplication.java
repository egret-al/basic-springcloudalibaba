package com.springcloudalibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 订单创建测试：http://localhost:9527/order-service/api/v1/order/create?userId=1&productId=1&count=10&money=100
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 18:02
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
