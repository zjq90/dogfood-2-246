package com.it.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.error("涓氬姟寮傚父: {}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("绯荤粺寮傚父: {}", e.getMessage(), e);
        return Result.error("绯荤粺寮傚父锛岃绋嶅悗閲嶈瘯");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("鍙傛暟寮傚父: {}", e.getMessage(), e);
        return Result.error("Parameter error: " + e.getMessage());
    }
}
