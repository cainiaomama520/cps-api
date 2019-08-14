package com.mex.pdd.config;

import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.mex.pdd.base.common.aspect.DataPaginationInterceptor;
import com.mex.pdd.base.common.aspect.MyJsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@MapperScan(basePackages = {
        "com.mex.pdd.modules.admin.sys.dao",
        "com.mex.pdd.modules.api.mapper",
})
public class MybatisPlusConfig {

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public DataPaginationInterceptor paginationInterceptor() {
        DataPaginationInterceptor paginationInterceptor = new DataPaginationInterceptor();
        paginationInterceptor.setLocalPage(true);// 开启 PageHelper 的支持
        paginationInterceptor.setSqlParser(new MyJsqlParserCountOptimize());
        return paginationInterceptor;
    }
}
