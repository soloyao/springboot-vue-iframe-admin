package com.zmy.springbootqx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.zmy.springbootqx.interceptor.LoginInterceptor;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: LoginConfiguration.java
 * @Description: 拦截配置类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:05:03
 */
@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {
	@Autowired LoginInterceptor loginInterceptor;
	
	/**
	 * 添加资源拦截处理器
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的springbootUpload目录下，访问路径如：http://localhost:8081/qx/upload/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中upload表示访问的前缀。"file:D:/springbootUpload/"是文件真实的存储路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/springbootUpload/");
        
        //swagger拦截
        registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
	}
	
	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)//添加登录拦截器
		.addPathPatterns("/**")//所有页面需要验证
		.excludePathPatterns("/login", "/register", "/demo", "/swagger-resources/**", "/v2/**", "/configuration/**", "/count");//这些url不需要拦截
		super.addInterceptors(registry);
	}
}

