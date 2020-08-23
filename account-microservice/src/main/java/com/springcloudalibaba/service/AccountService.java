package com.springcloudalibaba.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:02
 */
public interface AccountService {

    void decrease(@RequestParam("userId") Long userId, @RequestParam("money")BigDecimal money);
}
