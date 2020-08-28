package com.springcloudalibaba.service.impl;

import com.springcloudalibaba.dao.OrderDao;
import com.springcloudalibaba.domain.Order;
import com.springcloudalibaba.service.AccountService;
import com.springcloudalibaba.service.OrderService;
import com.springcloudalibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:08
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    @Transactional
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    public void createOrder(Order order) {
        log.info("创建订单");
        orderDao.createOrder(order);

        log.info("调用库存微服务");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("库存微服务完成");

        log.info("开始调用账户微服务");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("账户微服务完成");

        log.info("开始修改订单状态");
        orderDao.modifyOrderStatus(order.getUserId(), 0);
        log.info("修改状态完成");
    }
}
