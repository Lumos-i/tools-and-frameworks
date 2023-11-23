package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotation.RequestMany;
import org.example.factory.RequestManyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zrq
 * @ClassName RequestManyValidationAspect
 * @date 2023/11/22 9:14
 * @Description TODO
 */
@Aspect
@Component
public class RequestManyValidationAspect {
    @Autowired
    private Map<String, RequestManyStrategy> idempotentStrategies;

    @Around("@annotation(org.example.annotation.RequestMany)")
    public Object validateIdempotent(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        RequestMany requestMany = method.getAnnotation(RequestMany.class);
        String strategy = requestMany.value(); // 获取注解中配置的策略名称
        Integer time = (int)requestMany.expireTime(); // 获取注解中配置的策略名称
        if (!idempotentStrategies.containsKey(strategy)) {
            throw new IllegalArgumentException("Invalid idempotent strategy: " + strategy);
        }
        String key = generateKey(joinPoint); // 根据方法参数等生成唯一的key
        RequestManyStrategy idempotentStrategy = idempotentStrategies.get(strategy);
        idempotentStrategy.validate(key, time);
        return joinPoint.proceed();
    }

    private String generateKey(ProceedingJoinPoint joinPoint) {
        // 获取类名
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 获取方法名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        String argString = Arrays.stream(args)
                .map(Object::toString)
                .collect(Collectors.joining(","));
        // 获取请求携带的 Token
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        // 生成唯一的 key
        String key = className + ":" + methodName + ":" + argString + ":" + token;
        String md5Password = DigestUtils.md5DigestAsHex(key.getBytes());
        return md5Password;
    }

}
