package org.example.factory.impl;

import org.example.exception.RequestManyValidationException;
import org.example.factory.RequestManyStrategy;
import org.example.utils.RedisCache;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zrq
 * @ClassName TokenIdempotentStrategy
 * @date 2023/11/22 9:13
 * @Description TODO
 */
@Component
public class TokenIdempotentStrategy implements RequestManyStrategy {
    @Resource
    private RedisCache redisCache;
    @Override
    public void validate(String key, Integer time) throws RequestManyValidationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new RequestManyValidationException("未授权的token");
        }
        // 根据 key 和 token 执行幂等性校验
        boolean isDuplicateRequest = performTokenValidation(key, token);
        if (!isDuplicateRequest) {
            throw new RequestManyValidationException("多次请求");
        }
    }
    private boolean performTokenValidation(String key, String token) {
        // 执行根据 Token 进行幂等性校验的逻辑
        // 这里可以使用你选择的合适的方法，比如将 Token 存储到数据库或缓存中，然后检查是否已存在
        String storedToken = redisCache.getCacheObject(key);
        // 比较存储的 Token 和当前请求的 Token 是否一致
        return token.equals(storedToken);
    }

}
