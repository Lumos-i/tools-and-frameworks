package org.example.factory;

import org.example.exception.RequestManyValidationException;

/**
 * @author zrq
 * @ClassName RequestManyStrategy
 * @date 2023/11/22 9:04
 * @Description TODO
 */
public interface RequestManyStrategy {
    void validate(String key, Integer time) throws RequestManyValidationException;
}
