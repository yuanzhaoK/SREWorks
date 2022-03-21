package com.alibaba.sreworks.dataset.common;

import com.alibaba.sreworks.dataset.common.properties.ApplicationProperties;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author: fangzong.lyj@alibaba-inc.com
 * @date: 2021/09/15 10:46
 */

@Configuration
@MapperScan(basePackages = {"com.alibaba.sreworks.dataset.domain.primary"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasetSourceConfig {

//    @Bean(name = "dataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.dataset")
//    @Primary
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Autowired
    ApplicationProperties properties;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getDatasetUrl());
        dataSource.setUsername(properties.getDatasetUsername());
        dataSource.setPassword(properties.getDatasetPassword());
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/primary/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
