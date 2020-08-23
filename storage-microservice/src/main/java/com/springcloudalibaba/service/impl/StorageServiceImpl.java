package com.springcloudalibaba.service.impl;

import com.springcloudalibaba.dao.StorageDao;
import com.springcloudalibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:27
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("库存微服务开始");
        storageDao.decrease(productId, count);
        log.info("库存微服务结束");
    }
}
