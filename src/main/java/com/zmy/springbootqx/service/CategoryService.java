package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Category;

public interface CategoryService {
	List<Category> list(Map<String, String> paramMap);
	void add(Category category);
	void update(Category category);
	void delete(int id);
	Category get(int id);
}
