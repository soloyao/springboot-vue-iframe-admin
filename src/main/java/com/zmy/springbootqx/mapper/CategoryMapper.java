package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.Category;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: CategoryMapper.java
 * @Description: 类别数据访问层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:07:54
 */
@Mapper
public interface CategoryMapper {
	List<Category> list(Map<String, String> paramMap);
	void add(Category category);
	void delete(int id);
	void update(Category category);
	Category get(int id);
}
