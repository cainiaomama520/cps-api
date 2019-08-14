package com.mex.pdd.base.common.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月27日 下午9:59:27
 */
public class ResponseBean extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResponseBean() {
        put("code", 0);
        put("msg", "success");
    }

    public static ResponseBean error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ResponseBean error(String msg) {
        return error(500, msg);
    }

    public static ResponseBean error(HttpStatus code, String msg) {
        return error(code.value(), msg);
    }

    public static ResponseBean error(int code, String msg) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.put("code", code);
        responseBean.put("msg", msg);
        return responseBean;
    }

    public static ResponseBean ok(String msg) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.put("msg", msg);
        return responseBean;
    }

    public static ResponseBean ok(Map<String, Object> map) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.putAll(map);
        return responseBean;
    }

    public static ResponseBean ok() {
        return new ResponseBean();
    }

    public ResponseBean put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
