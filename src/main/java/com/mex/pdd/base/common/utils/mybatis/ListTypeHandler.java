package com.mex.pdd.base.common.utils.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.mex.pdd.base.common.enums.Country;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {
    private Class<T> clazz;
    private TypeReference<ArrayList<T>> typeRef;

    public ListTypeHandler(Class<T> clazz) {
        this.clazz = clazz;
        this.typeRef = new TypeReference<ArrayList<T>>() {
        };
    }

    public static void main(String[] args) {
        ArrayList<Country> list = Lists.newArrayList(Country.AD);
        String s = JSON.toJSONString(list);
        ListTypeHandler<Country> handler = new ListTypeHandler<>(Country.class);
        List<Country> countries = handler.parseJson(s);
        System.out.println(countries);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        String json = JSON.toJSONString(parameter);
        ps.setString(i, json);
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private List<T> parseJson(String json) {
        return JSON.parseArray(json, clazz);
    }
}
