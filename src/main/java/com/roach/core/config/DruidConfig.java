package com.roach.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author jdktomcat
 */
@Configuration
public class DruidConfig {

    private String baseUrl = System.getProperty("user.home");

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setTestWhileIdle(false);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        //发布路径
        datasource.setUrl("jdbc:derby:" + baseUrl + "/.redis_plus/data;create=true");
        datasource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        return datasource;
    }

}
