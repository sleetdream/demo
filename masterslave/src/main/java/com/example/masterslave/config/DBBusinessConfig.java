package com.example.masterslave.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages = "com.example.masterslave.mapper", sqlSessionTemplateRef  = "businessSqlSessionTemplate")
public class DBBusinessConfig {

    @Bean(name = "businessSqlSessionFactory")
    public SqlSessionFactory managerSqlSessionFactory(RoutingDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 下面的不可少，这个有了，就不用配置文件里的
        bean.setTypeAliasesPackage("com.example.masterslave.model");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapping/*Mapper.xml"));
        return bean.getObject();
    }

    @Bean(name = "businessTransactionManager")
    public DataSourceTransactionManager managerTransactionManager(RoutingDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "businessSqlSessionTemplate")
    public SqlSessionTemplate managerSqlSessionTemplate(@Qualifier("businessSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
