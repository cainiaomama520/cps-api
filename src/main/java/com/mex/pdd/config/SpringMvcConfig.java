package com.mex.pdd.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.mex.pdd.base.common.utils.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringMvcConfig {
    //修改messageConverter为 FastJson
    @Bean
    public FastJsonHttpMessageConverter fastjsonhttpmessageconverter() {
        class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
            public MyFastJsonHttpMessageConverter() {
                FastJsonConfig fastJsonConfig = new FastJsonConfig();
                fastJsonConfig.setDateFormat(Constant.DEFAULT_DATE_FORMAT);    // 自定义时间格式
                fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteBigDecimalAsPlain, SerializerFeature.WriteEnumUsingName); // 正常转换 null 值
                this.setFastJsonConfig(fastJsonConfig);
            }
        }
        return new MyFastJsonHttpMessageConverter();
    }
}
