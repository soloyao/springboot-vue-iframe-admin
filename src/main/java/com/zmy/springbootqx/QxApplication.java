package com.zmy.springbootqx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: QxApplication.java
 * @Description: 启动类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:14:14
 */
@SpringBootApplication
@ServletComponentScan
public class QxApplication {
	public static void main(String[] args) {
		SpringApplication.run(QxApplication.class, args);
	}
}