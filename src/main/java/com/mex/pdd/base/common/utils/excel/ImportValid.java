package com.mex.pdd.base.common.utils.excel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Created by yanchao on 2016/12/16.
 */
public class ImportValid {



    public static <T> void validClazz(List<T> lists) throws Exception {
        for (int i = 0; i < lists.size(); i++) {
            T t = lists.get(i);
            if (!validT(t)) {
                lists.remove(i);
            }
        }

        for (T t : lists) {
            fieldSet(t);
        }


    }

    public static <T> void validClazz(List<T> lists, List<String> metas) throws Exception {
        for (int i = 0; i < lists.size(); i++) {
            T t = lists.get(i);
            if (!validT(t, metas)) {
                lists.remove(i);
            }
        }

        for (T t : lists) {
            fieldSet(t);
        }


    }

    public static <T> void myValidClazz(List<T> lists) throws Exception {
        for (T t : lists) {
            if (Objects.nonNull(t)) {
                fieldSet1(t);
            }
        }
    }

    private static <T> void fieldSet1(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object va;
                va = field.get(t);
//                if ("campaignId".equals(field.getPIdNameList())) {
//                    va = va.toString().substring(0, va.toString().indexOf(".0"));
//                }
                field.set(t, va);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static <T> void fieldSet(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object va;
                va = field.get(t);
                if ((va instanceof String) && (va.toString().contains(".0"))) {
                    String filedv = va.toString().substring(0, va.toString().indexOf(".0"));
                    field.set(t, filedv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加字段验证 只要有任何一列为空这一行就不添加
     * (原方法不对)
     *
     * @param t
     * @return
     * @throws Exception
     */
    private static <T> boolean validT(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object va = field.get(t);
                //
                if (!"id".equals(field.getName()) && !"createTime".equals(field.getName()) && !"updateTime".equals(field.getName()) && !"mexLevel2Code".equals(field.getName()) && !"channelName".equals(field.getName())
                        && !"mexLevel2Name".equals(field.getName()) && !"mexLevel1Code".equals(field.getName()) && !"channelLevel1Code".equals(field.getName()) && !"channelCode".equals(field.getName()) && !"ctr".equals(field.getName())
                        && !"click".equals(field.getName()) && !"impression".equals(field.getName()) && !"wh".equals(field.getName()))
                    if (va == null || va.toString().equals("")) {
                        return false;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static <T> boolean validT(T t, List<String> metas) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object va = field.get(t);
                //
                long count = metas.stream().filter(f -> f.equals(field.getName())).count();
                if (0 != count) {
                    if (va == null || va.toString().equals("")) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
