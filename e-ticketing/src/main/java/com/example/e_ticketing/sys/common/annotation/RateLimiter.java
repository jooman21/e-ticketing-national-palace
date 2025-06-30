package com.example.e_ticketing.sys.common.annotation;

import com.palace.museum.common.constant.CacheConstants;
import com.palace.museum.common.enums.LimitType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter
{
    String key() default CacheConstants.RATE_LIMIT_KEY;

    int time() default 60;

    int count() default 100;

    LimitType limitType() default LimitType.DEFAULT;
}
