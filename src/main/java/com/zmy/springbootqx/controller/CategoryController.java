package com.zmy.springbootqx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.annotation.PermissionAnnotation;
import com.zmy.springbootqx.pojo.Category;
import com.zmy.springbootqx.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: CategoryController.java
 * @Description: 类别管理控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:05:52
 */
@RestController
@Api(value = "类别服务")
public class CategoryController {
	@Autowired CategoryService categoryService;
	
	/**
	 * 分页获取所有类别
	 * @param start 当前页
	 * @param size 一页记录条数
	 * @param keyword 关键词
	 * @return 分页对象
	 */
	@PermissionAnnotation(permName = "listCategory")
	@GetMapping("/categories")
	@LogAnnotation(desc = "分页获取所有类别")
	@ApiOperation(value = "分页获取所有类别")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "start", value = "当前页", defaultValue = "1", required = false, dataType = "int"),
		@ApiImplicitParam(paramType = "query", name = "size", value = "一页大小", defaultValue = "10", required = false, dataType = "int"),
		@ApiImplicitParam(paramType = "query", name = "keyword", value = "搜索关键词", defaultValue = "", required = false, dataType = "string")
	})
	public PageInfo<Category> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<Category> cs = categoryService.list(paramMap);
		PageInfo<Category> page = new PageInfo<Category>(cs, 5);
		return page;
	}
	
	/**
	 * 获取单个类别对象
	 * @param id 类别ID
	 * @return 类别对象
	 */
	@PermissionAnnotation(permName = "listCategory")
	@GetMapping("/categories/{id}")
	@LogAnnotation(desc = "获取单个类别")
	@ApiOperation(value = "获取单个类别")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id", value = "类别编号", required = true, dataType = "int")
	})
	public Category get(@PathVariable("id") int id) {
		Category category = categoryService.get(id);
		return category;
	}
	
	/**
	 * 新增分类
	 * @param category 类别对象
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "listCategory")
	@PostMapping("/categories")
	@LogAnnotation(desc = "新增分类")
	@ApiOperation(value = "新增分类")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "category", value = "类别对象", required = true, dataType = "Category")
	})
	public Object add(@RequestBody Category category) {
		return categoryService.add(category);
	}
	
	/**
	 * 修改分类
	 * @param category 类别对象
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "listCategory")
	@PutMapping("/categories")
	@LogAnnotation(desc = "修改分类")
	@ApiOperation(value = "修改分类")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "category", value = "类别对象", required = true, dataType = "Category")
	})
	public Object update(@RequestBody Category category) {
		return categoryService.update(category);
	}
	
	/**
	 * 删除分类
	 * @param id 类别ID
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "listCategory")
	@DeleteMapping("/categories/{id}")
	@LogAnnotation(desc = "删除分类")
	@ApiOperation(value = "删除分类")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path", name = "id", value = "类别编号", required = true, dataType = "int")
	})
	public Object delete(@PathVariable("id") int id) {
		return categoryService.delete(id);
	}
}
