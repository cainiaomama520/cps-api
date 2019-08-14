package com.mex.pdd.base.common.entity;

import com.alibaba.fastjson.JSON;
import com.mex.pdd.base.common.utils.mybatis.JsonTypeHandler;
import lombok.Data;

@Data
public class TestBean extends BaseBean {
    private String name;

    public static class TypeHandler extends JsonTypeHandler<TestBean> {
        public TypeHandler(Class<TestBean> clazz) {
            super(clazz);
        }
    }

    public static void main(String[] args) {
        TestBean testBean = new TestBean();
        testBean.setName("aaa");
        System.out.println(JSON.toJSONString(testBean));
    }
}
