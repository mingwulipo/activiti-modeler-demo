package com.test.activiti.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-bootstrap-ui默认访问地址是：http://${host}:${port}/doc.html
 */
@Configuration
@ConditionalOnProperty(value = "spring.swagger.enable", havingValue = "true")
@EnableSwagger2
public class Swagegr2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.test.activiti.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("activiti在线设计器demo接口文档")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl("http://127.0.0.1:8030/")
                .contact("lipo@rys.com")
                .version("1.0")
                .build();
    }
}
