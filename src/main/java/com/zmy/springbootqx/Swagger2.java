package com.zmy.springbootqx;

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
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: Swagger2.java
 * @Description: Swagger配置类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:14:25
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	/**
	   *     创建API应用
	 * apiInfo() 增加API相关信息
	   *     通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
	   *     本例采用指定扫描的包路径来定义指定要建立API的目录。
     * 
     * @return
     */

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zmy.springbootqx.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    /**
	   *    创建该API的基本信息（这些基本信息会展现在文档页面中）
	   *    访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("曾小瑶")
                .description("更多请关注http://www.zengxiaoyao.com")
                .termsOfServiceUrl("http://www.zengxiaoyao.com")
                .contact("zengxiaoyao")
                .version("1.0")
                .build();
    }

}
