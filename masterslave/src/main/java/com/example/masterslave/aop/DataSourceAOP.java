package com.example.masterslave.aop;

import com.example.masterslave.enums.DataSourceTypeEnum;
import com.example.masterslave.holder.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据源的切入面
 */
@Aspect
@Component
public class DataSourceAOP {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAOP.class);

    @Around("execution(* com.example.masterslave.mapper.*.insert*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.update*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.delete*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.add*(..))")
    public Object setWriteDataSourceType(ProceedingJoinPoint point) throws Throwable {
        DataSourceContextHolder.setDataSourceKey(DataSourceTypeEnum.MASTER.getName());
        logger.info("dataSource切换到：master");
        Object proceed = point.proceed();
        DataSourceContextHolder.clearDataSourceKey();
        return proceed;
    }

    @Around("execution(* com.example.masterslave.mapper.*.get*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.list*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.count*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.query*(..)) " +
            "|| execution(* com.example.masterslave.mapper.*.select*(..))")
    public Object setReadDataSourceType(ProceedingJoinPoint point) throws Throwable {
        DataSourceContextHolder.setDataSourceKey(DataSourceTypeEnum.SLAVE.getName());
        logger.info("dataSource切换到：slave");
        Object proceed = point.proceed();
        DataSourceContextHolder.clearDataSourceKey();
        return proceed;
    }


//    @Before("@annotation(targetDataSource)")
//    public void switchDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource){
//        if ( !DataSourceContextHolder.containDataSourceKey( targetDataSource.value().getName() ) ) {
//            logger.error("DataSource [{}] doesn't exist, use default DataSource [{}]", targetDataSource.value());
//        }else {
//            DataSourceContextHolder.setDataSourceKey( targetDataSource.value().getName() );
//            logger.info("Switch DataSource to [{}] in Method [{}]",
//                    DataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
//        }
//    }
//
//    @After("@annotation(targetDataSource))")
//    public void restoreDataSource(JoinPoint joinPoint,TargetDataSource targetDataSource){
//        DataSourceContextHolder.clearDataSourceKey();
//        logger.info("Restore DataSource to [{}] in Method [{}]",
//                DataSourceContextHolder.getDataSourceKey(), joinPoint.getSignature());
//    }
} 