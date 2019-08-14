/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mex.pdd.base.common.aspect;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.SqlParserHandler;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import com.baomidou.mybatisplus.plugins.parser.SqlInfo;
import com.baomidou.mybatisplus.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * <p>
 * 分页拦截器
 * </p>
 *
 * @author hubin
 * @Date 2016-01-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataPaginationInterceptor extends SqlParserHandler implements Interceptor {

    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(DataPaginationInterceptor.class);
    /**
     * COUNT SQL 解析
     */
    private ISqlParser sqlParser;
    /**
     * 溢出总页数，设置第一页
     */
    private boolean overflowCurrent = false;
    /**
     * 方言类型
     */
    private String dialectType;
    /**
     * 方言实现类
     */
    private String dialectClazz;
    /**
     * 是否开启 PageHelper localPage 模式
     */
    private boolean localPage = false;

    /**
     * Physical Pagination Interceptor for all the queries with parameter {@link RowBounds}
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
        String dataFilterSql = DataFilterThreadLocalContext.get();
        DataFilterThreadLocalContext.remove();
        //无权限过滤情况
        Boolean noDataFilter = (org.apache.commons.lang3.StringUtils.isBlank(dataFilterSql) || org.apache.commons.lang3.StringUtils.equals(" ()", dataFilterSql));
        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        if (!noDataFilter) {
            originalSql = buildDataFilter(originalSql, dataFilterSql);
            metaObject.setValue("delegate.boundSql.sql", originalSql);
        }

        /* 不需要分页的场合 */
        if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
            // 本地线程分页
            if (localPage) {
                // 采用ThreadLocal变量处理的分页
                rowBounds = PageHelper.getPagination();
                if (rowBounds == null) {
                    return invocation.proceed();
                }
            } else {
                // 无需分页
                return invocation.proceed();
            }
        }

        Connection connection = (Connection) invocation.getArgs()[0];
        DBType dbType = StringUtils.isNotEmpty(dialectType) ? DBType.getDBType(dialectType) : JdbcUtils.getDbType(connection.getMetaData().getURL());
        if (rowBounds instanceof Pagination) {
            Pagination page = (Pagination) rowBounds;
            boolean orderBy = true;
            if (page.isSearchCount()) {
                SqlInfo sqlInfo = SqlUtils.getOptimizeCountSql(page.isOptimizeCountSql(), sqlParser, originalSql);
                orderBy = sqlInfo.isOrderBy();
                this.queryTotal(overflowCurrent, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
                if (page.getTotal() <= 0) {
                    return invocation.proceed();
                }
            }
            String buildSql = SqlUtils.concatOrderBy(originalSql, page, orderBy);
            originalSql = DialectFactory.buildPaginationSql(page, buildSql, dbType, dialectClazz);
        } else {
            // support physical Pagination for RowBounds
            originalSql = DialectFactory.buildPaginationSql(rowBounds, originalSql, dbType, dialectClazz);
        }

        /*
         * <p> 禁用内存分页 </p>
         * <p> 内存分页会查询所有结果出来处理（这个很吓人的），如果结果变化频繁这个数据还会不准。</p>
         */
        metaObject.setValue("delegate.boundSql.sql", originalSql);
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        return invocation.proceed();
    }

    //如果线程变量中具有全下你过滤 过滤数据
    private String buildDataFilter(String sql, String dataFilterSql) {
        String orderBy = "ORDER BY";
        String groupBy = "GROUP BY";
        String beforeOrderSql = sql, afterOrderSql = "";
        if (sql.contains(groupBy)) {
            beforeOrderSql = sql.substring(0, sql.indexOf(groupBy));
            afterOrderSql = sql.substring(sql.indexOf(groupBy));
        } else if (sql.contains(orderBy)) {
            beforeOrderSql = sql.substring(0, sql.indexOf(orderBy));
            afterOrderSql = sql.substring(sql.indexOf(orderBy));
        }
        StringBuilder sb = new StringBuilder(beforeOrderSql);
        boolean hasWhere = beforeOrderSql.toUpperCase().contains("WHERE");
        if (!hasWhere) {
            sb.append(" WHERE ");
        } else {
            sb.append(" AND ");
        }
        sb.append(dataFilterSql).append("\n");
        sb.append(afterOrderSql);
        return sb.toString();
    }

    /**
     * 查询总记录条数
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    protected void queryTotal(boolean overflowCurrent, String sql, MappedStatement mappedStatement, BoundSql boundSql, Pagination page, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), boundSql);
            parameterHandler.setParameters(statement);
            long total = 0;
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getLong(1);
                }
            }
            page.setTotal(total);
            /*
             * 溢出总页数，设置第一页
             */
            long pages = page.getPages();
            if (overflowCurrent && (page.getCurrent() > pages)) {
                // 设置为第一条
                page.setCurrent(1);
            }
        } catch (Exception e) {
            logger.error("Error: Method queryTotal execution error !", e);
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        String localPage = prop.getProperty("localPage");

        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
        if (StringUtils.isNotEmpty(localPage)) {
            this.localPage = Boolean.valueOf(localPage);
        }
    }

    public DataPaginationInterceptor setDialectType(String dialectType) {
        this.dialectType = dialectType;
        return this;
    }

    public DataPaginationInterceptor setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
        return this;
    }

    public DataPaginationInterceptor setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
        return this;
    }

    public DataPaginationInterceptor setSqlParser(ISqlParser sqlParser) {
        this.sqlParser = sqlParser;
        return this;
    }

    public DataPaginationInterceptor setLocalPage(boolean localPage) {
        this.localPage = localPage;
        return this;
    }
}
