package com.mex.pdd.base.common.datasources;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017/8/19 1:03
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<String, DataSource> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(new HashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

}
