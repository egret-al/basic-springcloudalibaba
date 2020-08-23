package com.springcloudalibaba.service;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:26
 */
public interface StorageService {

    void decrease(Long productId, Integer count);
}
