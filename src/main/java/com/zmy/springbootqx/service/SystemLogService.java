package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.SystemLog;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: SystemLogService.java
 * @Description: 日志业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:12:13
 */
public interface SystemLogService {
	void save(SystemLog systemLog);
	List<SystemLog> list(Map<String, String> paramMap);
}
