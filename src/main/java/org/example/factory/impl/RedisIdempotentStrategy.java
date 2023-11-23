package org.example.factory.impl;

import org.example.exception.RequestManyValidationException;
import org.example.factory.RequestManyStrategy;
import org.example.utils.RedisCache;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zrq
 * @ClassName RedisIdempotentStrategy
 * @date 2023/11/22 9:07
 * @Description TODO
 */
@Component
public class RedisIdempotentStrategy implements RequestManyStrategy {
    @Resource
    private RedisCache redisCache;

    @Override
    public void validate(String key, Integer time) throws RequestManyValidationException {
        if (redisCache.hasKey(key)) {
            throw new RequestManyValidationException("请求次数过多");
        } else {
            redisCache.setCacheObject(key,"1", time, TimeUnit.MINUTES);
        }
    }
}
