package org.example.annotation;

import java.lang.annotation.*;

/**
 * @author zrq
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestMany {
    /**
     * 策略
     * @return
     */
    String value() default "";

    /**
     * 过期时间
     * @return
     */
    long expireTime() default 0;
}
