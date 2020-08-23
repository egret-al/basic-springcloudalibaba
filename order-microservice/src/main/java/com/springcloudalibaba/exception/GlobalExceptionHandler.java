package com.springcloudalibaba.exception;

import com.springcloudalibaba.domain.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/8/20 21:02
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e) {
        LOGGER.error("[ 系统异常 ]" + e);
        e.printStackTrace();
        return new CommonResult(-1, e.getMessage());
    }
}
