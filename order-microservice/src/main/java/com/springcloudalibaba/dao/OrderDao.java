package com.springcloudalibaba.dao;

import com.springcloudalibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/12 22:06
 */
@Mapper
public interface OrderDao {

    void createOrder(Order order);

    void modifyOrderStatus(@Param("userId") Long userId, @Param("status") Integer status);
}
