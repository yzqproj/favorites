package com.favorites.comm.aop;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Leo Wu
 * @version 1.0
 * @Description: 日志管理
 * @date 2016年7月6日  下午5:36:38
 */
@Aspect
@Component
@Slf4j
public class LoggerAdvice {
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut(value = "within(com.favorites..*) && @annotation(LoggerManage)")
    public void pointCut(){

    }

    @Before( "within(com.favorites..*) && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        startTime.set(System.currentTimeMillis());
        log .info("执行 " + loggerManage.description() + " 开始");

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("请求URL : " + request.getRequestURL().toString());
        log.info("请求HTTP_METHOD : " + request.getMethod());
        log.info("请求IP : " + request.getRemoteAddr());
        log.info("请求CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数值 : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret",pointcut = "within(com.favorites..*) && @annotation(loggerManage)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage loggerManage ,Object ret) {
        log .info("执行 " + loggerManage.description() + " 结束");
        log.info("响应RESPONSE : " + ret);
        log.info("响应时间SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    @AfterThrowing(pointcut = "within(com.favorites..*) && @annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        log .error("执行 " + loggerManage.description() + " 异常", ex);
    }

}
