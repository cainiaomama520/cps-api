package com.mex.pdd.base.common.utils.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.mex.pdd.modules.api.entity.Project;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@MappedJdbcTypes(value = JdbcType.VARCHAR,includeNullJdbcType = true)
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex), clazz);
    }

    private String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private T toObject(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return (T) mapper.readValue(content, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
//        List<Integer> list = ImmutableList.of(1, 2, 3);
        Project project = new Project();
        project.setName("ada");
        ImmutableList<Project> list = ImmutableList.of(project);

        JsonTypeHandler listJsonTypeHandler = new JsonTypeHandler(List.class);
        String s = listJsonTypeHandler.toJson(list);
        System.out.println(s);
        List list1 = (List) listJsonTypeHandler.toObject(s, List.class);
        System.out.println(list1);
        System.out.println(list.equals(list1));
//
//        System.out.println(javaType.getClass());
//        Object o = mapper.readValue(s, javaType);
//        System.out.println(o);

//        String s1 = JSON.toJSONString(list);
//        System.out.println(s1);
//        List<Integer> list1 = JSON.parseArray(s1, Integer.class);
//        System.out.println(JSON.parseArray(s1,Integer.class));
//        System.out.println(list.equals(list1));
    }

    static {
//        mapper.configure(Feature.WRITE_NULL_MAP_VALUES, false);
//        mapper.setSerializationInclusion(Inclusion.NON_NULL);
    }
}
