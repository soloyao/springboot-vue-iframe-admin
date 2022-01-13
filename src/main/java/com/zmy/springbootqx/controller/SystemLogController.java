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
import com.zmy.springbootqx.annotation.PermissionAnnotation;
import com.zmy.springbootqx.pojo.SystemLog;
import com.zmy.springbootqx.service.SystemLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: SystemLogController.java
 * @Description: 日志管理控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:07:06
 */
@RestController
@Api(value = "日志查询")
public class SystemLogController {
	@Autowired SystemLogService systemLogService;
	
	/**
	 * 分页获取所有日志
	 * @param start
	 * @param size
	 * @param keyword
	 * @return
	 */
	@PermissionAnnotation(permName = "listSystemLog")
	@GetMapping("/systemLogs")
	@LogAnnotation(desc = "分页获取所有日志")
	@ApiOperation(value = "分页获取所有日志")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "start", value = "当前页", defaultValue = "1", required = false, dataType = "int"),
		@ApiImplicitParam(paramType = "query", name = "size", value = "一页大小", defaultValue = "10", required = false, dataType = "int"),
		@ApiImplicitParam(paramType = "query", name = "keyword", value = "搜索关键词", defaultValue = "", required = false, dataType = "string")
	})
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
