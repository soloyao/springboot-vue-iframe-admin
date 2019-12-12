package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.CategoryMapper;
import com.zmy.springbootqx.pojo.Category;
import com.zmy.springbootqx.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired CategoryMapper categoryMapper;
	
	@Override
	public List<Category> list(Map<String, String> paramMap) {
		return categoryMapper.list(paramMap);
	}

	@Override
	public void add(Category category) {
		categoryMapper.add(category);
	}

	@Override
	public void update(Category category) {
		categoryMapper.update(category);
	}

	@Override
	public void delete(int id) {
		categoryMapper.delete(id);
	}

	@Override
	public Category get(int id) {
		return categoryMapper.get(id);
	}
	
}
