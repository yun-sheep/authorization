package com.yun.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @Description Swagger配置依赖
 * @auther j2-yizhiyang
 * @date 2023/3/6 21:04
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("认证授权-demo RESTful APIs")
                        .description("#用于认证授权demo的RESTful APIs提供说明")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .contact(new Contact("云学习", "","1411770226@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("认证授权")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.yun.authorization.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
