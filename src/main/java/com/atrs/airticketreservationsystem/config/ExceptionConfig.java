package com.atrs.airticketreservationsystem.config;

import com.atrs.airticketreservationsystem.entity.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(RuntimeException.class)
    public JsonResponse handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        return JsonResponse.error("网络异常");
    }
}
