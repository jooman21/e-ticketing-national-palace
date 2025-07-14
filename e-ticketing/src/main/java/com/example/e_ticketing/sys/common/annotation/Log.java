package com.example.e_ticketing.sys.common.annotation;

import com.example.e_ticketing.sys.common.enums.RequestType;
import com.example.e_ticketing.sys.common.enums.OperatorType;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    String title() default "";

    RequestType businessType() default RequestType.OTHER;

    OperatorType operatorType() default OperatorType.MANAGE;

    boolean isSaveRequestData() default true;

    boolean isSaveResponseData() default true;

    String[] excludeParamNames() default {};
}
