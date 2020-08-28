package com.springcloudalibaba.service.impl;

import com.springcloudalibaba.dao.AccountDao;
import com.springcloudalibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:03
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("账户微服务开始");
        int i = 1 / 0;
        accountDao.decrease(userId, money);
        log.info("账户微服务完成");
    }
}
