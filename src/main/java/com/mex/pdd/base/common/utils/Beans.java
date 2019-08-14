package com.mex.pdd.base.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Beans extends org.springframework.beans.BeanUtils {

    public static List<String> getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        final List<String> prop = Stream.of(pds)
                .filter(p -> Objects.isNull(srcBean.getPropertyValue(p.getName())))
                .map(FeatureDescriptor::getName).collect(Collectors.toList());
        return prop;
    }

    public static List<String> getNonNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        final List<String> prop = Stream.of(pds)
                .filter(p -> Objects.nonNull(srcBean.getPropertyValue(p.getName())))
                .map(FeatureDescriptor::getName).collect(Collectors.toList());
        return prop;
    }

    public static Map<String, String> beanToStringMap(Object bean) {
        Map<String, String> map = new HashMap<>();
        //获取指定类（Person）的BeanInfo 对象
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String key = pd.getName();
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(bean);
                map.put(key, String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //JavaBean转换为Map
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        //获取指定类（Person）的BeanInfo 对象
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass(), Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String key = pd.getName();
                Method getter = pd.getReadMethod();
                Object value = getter.invoke(bean);
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clz) {
        //创建JavaBean对象
        T obj = null;
        try {
            obj = clz.newInstance();
            //获取指定类的BeanInfo对象
            BeanInfo beanInfo = Introspector.getBeanInfo(clz, Object.class);
            //获取所有的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                Object value = map.get(pd.getName());
                Method setter = pd.getWriteMethod();
                setter.invoke(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
