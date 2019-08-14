package com.mex.pdd.base.common.utils;


import com.alibaba.fastjson.JSON;
import com.mex.pdd.base.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 查询参数
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-10-14 23:15
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int page;
    //每页条数
    private int limit;

    private String sidx;
    private String order;
    private Condition pageCondition;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        if (StringUtils.isNotBlank(sidx)) {
            sidx = SQLFilter.sqlInject(sidx);
            this.put("sidx", sidx);
            this.sidx = sidx;
        }
        if (StringUtils.isNotBlank(order)) {
            order = SQLFilter.sqlInject(order);
            this.put("order", order);
            this.order = order;
        }
        //模糊查询
        Object filters = params.get("filters");
        if (Objects.nonNull(filters)) {
            String condition = String.valueOf(filters).replaceAll("&quot;", "\"");
            Condition pageCondition = JSON.parseObject(condition, Condition.class);
            this.put("condition", pageCondition.dealConditon());
            this.pageCondition = pageCondition;
        }


    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Condition getPageCondition() {
        return pageCondition;
    }

    public void setPageCondition(Condition pageCondition) {
        this.pageCondition = pageCondition;
    }

}
