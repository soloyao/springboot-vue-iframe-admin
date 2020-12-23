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
import com.zmy.springbootqx.pojo.Hotel;
import com.zmy.springbootqx.service.HotelService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: HotelController.java
 * @Description: 旅馆管理控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:06:29
 */
@ApiIgnore
@RestController
public class HotelController {
	@Autowired HotelService hotelService;
	
	/**
	 * 分页获取所有旅馆
	 * @param start 当前页
	 * @param size 一页记录条数
	 * @param keyword 搜索关键词
	 * @return 分页对象
	 */
	@GetMapping("/hotels")
	@LogAnnotation(desc = "分页获取所有旅馆数据")
	public PageInfo<Hotel> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<Hotel> hs = hotelService.list(paramMap);
		PageInfo<Hotel> page = new PageInfo<Hotel>(hs, 5);
		return page;
	}
	
	/**
	 * 获取单个旅馆
	 * @param id 旅馆ID
	 * @return 旅馆对象
	 */
	@GetMapping("/hotels/{id}")
	@LogAnnotation(desc = "获取单个旅馆")
	public Hotel get(@PathVariable("id") int id) {
		Hotel hotel = hotelService.get(id);
		return hotel;
	}
	
	/**
	 * 新增旅馆
	 * @param hotel 旅馆对象
	 * @return Result对象（成功或失败）
	 */
	@PostMapping("/hotels")
	@LogAnnotation(desc = "新增旅馆")
	public Object add(@RequestBody Hotel hotel) {
		return hotelService.add(hotel);
	}
	
	/**
	 * 修改旅馆
	 * @param hotel 旅馆对象
	 * @return Result对象（成功或失败）
	 */
	@PutMapping("/hotels")
	@LogAnnotation(desc = "修改旅馆")
	public Object update(@RequestBody Hotel hotel) {
		return hotelService.update(hotel);
	}
	
	/**
	 * 删除旅馆
	 * @param id 旅馆ID
	 * @return Result对象（成功或失败）
	 */
	@DeleteMapping("/hotels/{id}")
	@LogAnnotation(desc = "删除旅馆")
	public Object delete(@PathVariable("id") int id) {
		return hotelService.delete(id);
	}
}
