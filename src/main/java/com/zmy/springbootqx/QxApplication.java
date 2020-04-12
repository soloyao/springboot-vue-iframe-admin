package com.zmy.springbootqx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class QxApplication {
	public static void main(String[] args) {
		SpringApplication.run(QxApplication.class, args);
	}
}