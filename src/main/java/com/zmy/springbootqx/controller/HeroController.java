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
import com.zmy.springbootqx.pojo.Hero;
import com.zmy.springbootqx.service.HeroService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: HeroController.java
 * @Description: 英雄管理控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:06:06
 */
@ApiIgnore
@RestController
public class HeroController {
	@Autowired HeroService heroService;
	
	/**
	 * 分页获取所有英雄
	 * @param start 当前页
	 * @param size 一页记录条数
	 * @param keyword 搜索关键词
	 * @return 分页对象
	 */
	@PermissionAnnotation(permName = "listHero")
	@GetMapping("/heros")
	@LogAnnotation(desc = "分页获取所有英雄")
	public PageInfo<Hero> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<Hero> hs = heroService.list(paramMap);
		PageInfo<Hero> page = new PageInfo<Hero>(hs, 5);
		return page;
	}
	
	/**
	 * 获取单个英雄对象
	 * @param id 英雄ID
	 * @return 英雄对象
	 */
	@PermissionAnnotation(permName = "listHero")
	@GetMapping("/heros/{id}")
	@LogAnnotation(desc = "获取单个英雄")
	public Hero get(@PathVariable("id") int id) {
		Hero hero = heroService.get(id);
		return hero;
	}
	
	/**
	 * 新增英雄
	 * @param hero 英雄对象
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "addHero")
	@PostMapping("/heros")
	@LogAnnotation(desc = "新增英雄")
	public Object add(@RequestBody Hero hero) {
		return heroService.add(hero);
	}
	
	/**
	 * 修改英雄
	 * @param hero 英雄对象
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "updateHero")
	@PutMapping("/heros")
	@LogAnnotation(desc = "修改英雄")
	public Object update(@RequestBody Hero hero) {
		return heroService.update(hero);
	}
	
	/**
	 * 删除英雄
	 * @param id 英雄ID
	 * @return Result对象（成功或失败）
	 */
	@PermissionAnnotation(permName = "deleteHero")
	@DeleteMapping("/heros/{id}")
	@LogAnnotation(desc = "删除英雄")
	public Object delete(@PathVariable("id") int id) {
		return heroService.delete(id);
	}
}
