package com.wei.boot.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * api 生成
 * @author zxc
 *
 */
@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    @Bean
    Docket docket() {
        Parameter parameter = new ParameterBuilder()
                .name("token")
                .description("token")
                .required(true)
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .build();
        
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wei.boot.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Lists.newArrayList(parameter));
    }
}
