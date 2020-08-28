##nacos注册中心配置文件

### order-service-default.yaml

```yml
spring:
  # 引入redis
  redis:
    database: 0
    host: 101.200.203.216
    port: 6379
  # 链路追踪
  zipkin:
    base-url: http://101.200.203.216:9411/
  sleuth:
    sampler:
      probability: 1
  cloud:
    sentinel:
      transport:
        # 配置sentinel dashboard地址
        dashboard: localhost:8080
        port: 8791
      eager: true
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://101.200.203.216:3306/t_order?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: 2243756824
  
mybatis:
  mapper-locations: classpath:mapper/*.xml
management:
  endpoints:
    web:
      exposure:
        include: '*'

seata:
  enabled: true
  application-id: order-server
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  config:
    type: nacos
    nacos:
      namespace:
      serverAddr: 101.200.203.216:8848
      group: SEATA_GROUP
      userName: "nacos"
      password: "nacos"
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 101.200.203.216:8848
      namespace:
      userName: "nacos"
      password: "nacos"
```

### account-service-default.yaml

```yml
spring:
  # 引入redis
  redis:
    database: 0
    host: 101.200.203.216
    port: 6379
  # 链路追踪
  zipkin:
    base-url: http://101.200.203.216:9411/
  sleuth:
    sampler:
      probability: 1
  cloud: 
    sentinel:
      transport:
        # 配置sentinel dashboard地址
        dashboard: localhost:8080
        port: 8719
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://101.200.203.216:3306/t_account?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: 2243756824

mybatis:
  mapper-locations: classpath:mapper/*.xml
management:
  endpoints:
    web:
      exposure:
        include: '*'

seata:
  enabled: true
  application-id: account-server
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  config:
    type: nacos
    nacos:
      namespace:
      serverAddr: 101.200.203.216:8848
      group: SEATA_GROUP
      userName: "nacos"
      password: "nacos"
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 101.200.203.216:8848
      namespace:
      userName: "nacos"
      password: "nacos"
```

###storage-service-default.yaml

```yml
spring:
  # 引入redis
  redis:
    database: 0
    host: 101.200.203.216
    port: 6379
  # 链路追踪
  zipkin:
    base-url: http://101.200.203.216:9411/
  sleuth:
    sampler:
      probability: 1
  cloud:
    sentinel:
      transport:
        # 配置sentinel dashboard地址
        dashboard: localhost:8080
        port: 8791
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://101.200.203.216:3306/t_storage?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8
    username: root
    password: 2243756824

mybatis:
  mapper-locations: classpath:mapper/*.xml
management:
  endpoints:
    web:
      exposure:
        include: '*'
        
seata:
  enabled: true
  application-id: storage-server
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  config:
    type: nacos
    nacos:
      namespace:
      serverAddr: 101.200.203.216:8848
      group: SEATA_GROUP
      userName: "nacos"
      password: "nacos"
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: 101.200.203.216:8848
      namespace:
      userName: "nacos"
      password: "nacos"
```

### gateway-service-default.yaml

```yml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          lower-case-service-id: true
          enabled: true
      routes:
        - id: account-service
          uri: lb://account-service
          predicates:
            - Path=/api/v1/account/**

        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/v1/order/**
        
        - id: storage-service
          uri: lb://storage-service
          predicates:
            - Path=/api/v1/storage/**
```

### 项目数据库

```sql
use t_order;
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
    `id`        bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(20)     DEFAULT NULL COMMENT '用户id',
    `product_id` bigint(11)     DEFAULT NULL COMMENT '产品id',
    `count`      int(11)        DEFAULT NULL COMMENT '数量',
    `money`      decimal(11, 0) DEFAULT NULL COMMENT '金额',
    `status`     int(1)         DEFAULT NULL COMMENT '订单状态:  0:创建中 1:已完结',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '订单表'
  ROW_FORMAT = Dynamic;

use t_storage;
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage`
(
    `id`        bigint(11) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
    `total`      int(11)    DEFAULT NULL COMMENT '总库存',
    `used`       int(11)    DEFAULT NULL COMMENT '已用库存',
    `residue`    int(11)    DEFAULT NULL COMMENT '剩余库存',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '库存'
  ROW_FORMAT = Dynamic;
INSERT INTO `t_storage`
VALUES (1, 1, 100, 0, 100);

use t_account;
CREATE TABLE `t_account`
(
    `id`      bigint(11) NOT NULL COMMENT 'id',
    `user_id` bigint(11)     DEFAULT NULL COMMENT '用户id',
    `total`   decimal(10, 0) DEFAULT NULL COMMENT '总额度',
    `used`    decimal(10, 0) DEFAULT NULL COMMENT '已用余额',
    `residue` decimal(10, 0) DEFAULT NULL COMMENT '剩余可用额度',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '账户表'
  ROW_FORMAT = Dynamic;

INSERT INTO `t_account`
VALUES (1, 1, 1000, 0, 1000);
```

