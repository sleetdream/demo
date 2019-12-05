package com.example.masterslave.config;

import com.example.masterslave.enums.DataSourceTypeEnum;
import com.example.masterslave.holder.DataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.master.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master(){
        logger.info("-------------------- masterDataSource init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slave() {
        logger.info("-------------------- slaveDataSource init ---------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean
    public RoutingDataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>();
        dataSourceMap.put(DataSourceTypeEnum.MASTER.getName(), master());
        dataSourceMap.put(DataSourceTypeEnum.SLAVE.getName(), slave());

        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(dataSourceMap);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(master());

        DataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
        return dataSource;
    }
} 