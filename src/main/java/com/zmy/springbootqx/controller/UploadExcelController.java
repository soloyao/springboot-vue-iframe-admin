package com.zmy.springbootqx.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.Hero;
import com.zmy.springbootqx.service.HeroService;
import com.zmy.springbootqx.util.ExcelReaderUtil;
import com.zmy.springbootqx.util.ExcelWriterUtil;
import com.zmy.springbootqx.util.Result;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UploadExcelController.java
 * @Description: Excel导入
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年12月25日 上午9:50:57
 */
@ApiIgnore
@RestController
public class UploadExcelController {

	private static String[] CELL_HEADS_HERO = {"名称", "血量"};
	
	@Autowired HeroService heroService;
	
	@PostMapping("/uploadExcelHero")
	@LogAnnotation(desc = "导入英雄Excel文件")
	public String uploadExcelHero(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		Object result = ExcelReaderUtil.readExcel(file);
		if (result instanceof List) {
			List<Hero> list = (List<Hero>) result;
			return heroService.addForeach(list);
		} else {
			return (String) result;
		}
	}
	
	@PostMapping("/downloadExcelHero")
	@LogAnnotation(desc = "导出英雄Excel文件")
	public Result downloadExcelHero(@RequestBody JSONObject params) {
		String[] heroIds = params.get("heroIds").toString().split(",");
		List<Hero> heroList = heroService.listByIds(heroIds);
		return ExcelWriterUtil.exportData(heroList, CELL_HEADS_HERO);
	}
	
}
