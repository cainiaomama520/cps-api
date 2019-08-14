package com.mex.pdd.base.common.utils.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapStringTypeHandler<T> extends BaseTypeHandler<Map<String, T>> {

    private Class<T> clazz;
    private final TypeReference<HashMap<String, T>> typeRef = new TypeReference<HashMap<String, T>>() {
    };

    public MapStringTypeHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, T> parameter, JdbcType jdbcType) throws SQLException {
        String json = JSON.toJSONString(parameter);
        ps.setString(i, json);
    }

    @Override
    public Map<String, T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public Map<String, T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public Map<String, T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private Map<String, T> parseJson(String json) {
        if(StringUtils.isBlank(json)){
            return Maps.newHashMap();
        }
        if (String.class == clazz || Integer.class == clazz || Boolean.class == clazz) {
            return JSON.parseObject(json, typeRef);
        }
        Map<String, T> result = new HashMap<>();
        JSON.parseObject(json).forEach((k, v) -> result.put(k, JSON.parseObject(JSON.toJSONString(v), clazz)));
        return result;
    }
}