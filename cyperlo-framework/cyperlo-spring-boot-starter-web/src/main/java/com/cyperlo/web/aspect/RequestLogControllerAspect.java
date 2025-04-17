package com.cyperlo.web.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cyperlo.common.utils.JacksonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class RequestLogControllerAspect {
    @Pointcut("execution(public * com.cyperlo.*.controller.*.*(..))")
    public void webLog() {}


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         assert attributes != null;
         HttpServletRequest request = attributes.getRequest();
         StringBuilder logBuilder = new StringBuilder();
         logBuilder.append(
                 "========================================== Start ==========================================\n");
         logBuilder.append("URL            : {}\n");
         logBuilder.append("HTTP Method    : {}\n");
         logBuilder.append("Class Method   : {}.{}\n");
         logBuilder.append("IP             : {}\n");
         logBuilder.append("Request Args   : {}\n");
         log.info(logBuilder.toString(),
                 request.getRequestURL().toString(),
                 request.getMethod(),
                 joinPoint.getSignature().getDeclaringTypeName(),
                 joinPoint.getSignature().getName(),
                 request.getRemoteAddr(),
                 JacksonUtil.toJson(filterArgs(joinPoint.getArgs())));
    }

    /**
     * 过滤请求参数
     * @param args 请求参数
     * @return 过滤后的请求参数
     */
    private List<Object> filterArgs(Object[] args) {
        return Arrays.stream(args).filter(o -> !(o instanceof MultipartFile) &&
                !(o instanceof HttpServletRequest) &&
                !(o instanceof HttpServletResponse)).collect(Collectors.toList());
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Response Args  : {}\n");
        logBuilder.append("Time-Consuming : {} ms\n");
        logBuilder.append(
                "=========================================== End ===========================================\n");
        // 获取响应体
        Object responseBody = null;
        try {
            responseBody = JacksonUtil.toJson(result);
        } catch (Exception e) {
            responseBody = result;
        }
        log.info(logBuilder.toString(),
                responseBody,
                System.currentTimeMillis() - startTime);
        return result;
    }
}
