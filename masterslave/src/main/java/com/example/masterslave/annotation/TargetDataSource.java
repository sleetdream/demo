package com.example.masterslave.annotation;

import com.example.masterslave.enums.DataSourceTypeEnum;

import java.lang.annotation.*;

/**
 * 使用主库的注解
 */
@Retention(RetentionPolicy.RUNTIME)//运行时保留
@Documented//生成到文档中
@Target({ElementType.METHOD, ElementType.TYPE})//作用范围
public @interface TargetDataSource  {
    DataSourceTypeEnum value() default DataSourceTypeEnum.MASTER;
}
