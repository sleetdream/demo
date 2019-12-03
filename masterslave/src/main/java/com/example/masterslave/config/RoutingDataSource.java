package com.example.masterslave.config;


import com.example.masterslave.holder.DataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;



/**
 * 动态数据源
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("current datasource is : {}", DataSourceContextHolder.getDataSourceKey());
        return DataSourceContextHolder.getDataSourceKey();
    }
} 