package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.SystemLog;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: SystemLogMapper.java
 * @Description: 日志数据访问层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:08:49
 */
@Mapper
public interface SystemLogMapper {
	void save(SystemLog systemLog);
	List<SystemLog> list(Map<String, String> paramMap);
}
