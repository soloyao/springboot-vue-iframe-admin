package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Category;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: CategoryService.java
 * @Description: 类别业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:11:32
 */
public interface CategoryService {
	List<Category> list(Map<String, String> paramMap);
	Object add(Category category);
	Object update(Category category);
	Object delete(int id);
	Category get(int id);
}
