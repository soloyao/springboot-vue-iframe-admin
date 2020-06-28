package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.SystemLogMapper;
import com.zmy.springbootqx.pojo.SystemLog;
import com.zmy.springbootqx.service.SystemLogService;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: SystemLogServiceImpl.java
 * @Description: 日志业务接口实现类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:13:29
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {
	@Autowired SystemLogMapper systemLogMapper;
	
	@Override
	public void save(SystemLog systemLog) {
		systemLogMapper.save(systemLog);
	}

	@Override
	public List<SystemLog> list(Map<String, String> paramMap) {
		return systemLogMapper.list(paramMap);
	}

}
