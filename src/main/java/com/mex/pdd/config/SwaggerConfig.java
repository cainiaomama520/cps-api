package com.mex.pdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * swagger配置类
 *
 * @author fengshuonan
 * @date 2017年6月1日19:42:59
 */
@Configuration
@EnableSwagger2
//@ConditionalOnProperty(prefix = "lottery", pIdNameList = "swagger-open", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket innerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        Collections.singletonList(new ParameterBuilder()
                                .name("token")
                                .description("用户token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()))
                .groupName("admin-api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true).select()
                .apis(RequestHandlerSelectors.basePackage("com.mex.pdd.modules.admin"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(innerApiInfo());

    }

    @Bean
    public Docket mobileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(
//                        Collections.singletonList(new ParameterBuilder()
//                                .pIdNameList("token")
//                                .description("用户token")
//                                .modelRef(new ModelRef("string"))
//                                .parameterType("header")
//                                .required(false)
//                                .build()))
                .groupName("public-api")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true).select()
                .apis(RequestHandlerSelectors.basePackage("com.mex.pdd.modules.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(openApiInfo());

    }


    private ApiInfo innerApiInfo() {
        return new ApiInfoBuilder()
                .title("PDD sys-api")//大标题
                .description("系统api")//详细描述
                .version("1.0") //版本
                .termsOfServiceUrl("NO terms of service")
                .build();
    }

    private ApiInfo openApiInfo() {
        return new ApiInfoBuilder()
                .title("PDD public-api")//大标题
                .description("公共api")//详细描述
                .version("1.0") //版本
                .termsOfServiceUrl("NO terms of service")
                .build();
    }

}