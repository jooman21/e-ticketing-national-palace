package com.example.e_ticketing.sys.common.annotation;

import com.palace.museum.common.enums.DataSourceType;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    public DataSourceType value() default DataSourceType.MASTER;
}
