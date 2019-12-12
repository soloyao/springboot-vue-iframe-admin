package com.zmy.springbootqx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.SystemLog;
import com.zmy.springbootqx.service.SystemLogService;

@RestController
public class SystemLogController {
	@Autowired SystemLogService systemLogService;
	
	@GetMapping("/systemLogs")
	@LogAnnotation(desc = "分页获取所有日志")
	public PageInfo<SystemLog> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "15") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<SystemLog> ss = systemLogService.list(paramMap);
		PageInfo<SystemLog> page = new PageInfo<SystemLog>(ss, 5);
		return page;
	}
}
