package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.CategoryMapper;
import com.zmy.springbootqx.pojo.Category;
import com.zmy.springbootqx.service.CategoryService;
import com.zmy.springbootqx.util.Result;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: CategoryServiceImpl.java
 * @Description: 类别业务接口实现类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:12:32
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired CategoryMapper categoryMapper;
	
	@Override
	public List<Category> list(Map<String, String> paramMap) {
		return categoryMapper.list(paramMap);
	}

	@Override
	public Object add(Category category) {
		categoryMapper.add(category);
		return Result.success();
	}

	@Override
	public Object update(Category category) {
		categoryMapper.update(category);
		return Result.success();
	}

	@Override
	public Object delete(int id) {
		categoryMapper.delete(id);
		return Result.success();
	}

	@Override
	public Category get(int id) {
		return categoryMapper.get(id);
	}
	
}
