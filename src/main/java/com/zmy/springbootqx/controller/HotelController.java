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

@ApiIgnore
@RestController
public class HotelController {
	@Autowired HotelService hotelService;
	
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
	
	@GetMapping("/hotels/{id}")
	@LogAnnotation(desc = "获取单个旅馆")
	public Hotel get(@PathVariable("id") int id) {
		Hotel hotel = hotelService.get(id);
		return hotel;
	}
	
	@PostMapping("/hotels")
	@LogAnnotation(desc = "新增旅馆")
	public Object add(@RequestBody Hotel hotel) {
		return hotelService.add(hotel);
	}
	
	@PutMapping("/hotels")
	@LogAnnotation(desc = "修改旅馆")
	public Object update(@RequestBody Hotel hotel) {
		return hotelService.update(hotel);
	}
	
	@DeleteMapping("/hotels/{id}")
	@LogAnnotation(desc = "删除旅馆")
	public Object delete(@PathVariable("id") int id) {
		return hotelService.delete(id);
	}
}
