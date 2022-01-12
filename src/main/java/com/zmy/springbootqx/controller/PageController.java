package com.zmy.springbootqx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.annotation.PermissionAnnotation;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: PageController.java
 * @Description: 页面跳转控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:06:40
 */
@ApiIgnore
@Controller
public class PageController {
	/**
	 * 跳转至默认首页
	 * @return
	 */
	@GetMapping("/")
	@LogAnnotation(desc = "跳转至默认首页")
	public ModelAndView defaultIndex() {
		ModelAndView mav = new ModelAndView("index/index");
		return mav;
	}
	
	/**
	 * 跳转至登录页面
	 * @return
	 */
	@GetMapping("/login")
	@LogAnnotation(desc = "跳转至登录页面")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login/login");
		return mav;
	}
	
	/**
	 * 跳转至home页面
	 * @return
	 */
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home/home");
		return mav;
	}
	
	@GetMapping("/demo")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("demo");
		return mav;
	}
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@GetMapping("/index")
	@LogAnnotation(desc = "跳转至首页")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index/index");
		return mav;
	}
	
	/**
	 * 跳转至数据监控页面
	 * @return
	 */
	@PermissionAnnotation(permName = "druidServer")
	@GetMapping("/druidServer")
	@LogAnnotation(desc = "跳转至数据监控页面")
	public ModelAndView druidServer() {
		ModelAndView mav = new ModelAndView("druid/druidServer");
		return mav;
	}
	
	/**
	 * 跳转至类别管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listCategory")
	@GetMapping("/listCategory")
	@LogAnnotation(desc = "跳转至分类管理页面")
	public ModelAndView listCategory() {
		ModelAndView mav = new ModelAndView("category/listCategory");
		return mav;
	}
	
	/**
	 * 跳转至用户管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listUser")
	@GetMapping("/listUser")
	@LogAnnotation(desc = "跳转至用户管理页面")
	public ModelAndView listUser() {
		ModelAndView mav = new ModelAndView("user/listUser");
		return mav;
	}
	
	/**
	 * 跳转至角色管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listRole")
	@GetMapping("/listRole")
	@LogAnnotation(desc = "跳转至角色管理页面")
	public ModelAndView listRole() {
		ModelAndView mav = new ModelAndView("role/listRole");
		return mav;
	}
	
	/**
	 * 跳转至菜单管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listPermission")
	@GetMapping("/listPermission")
	@LogAnnotation(desc = "跳转至菜单管理页面")
	public ModelAndView listPermission() {
		ModelAndView mav = new ModelAndView("permission/listPermission");
		return mav;
	}
	
	/**
	 * 跳转至旅馆管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listHotel")
	@GetMapping("/listHotel")
	@LogAnnotation(desc = "跳转至旅馆管理页面")
	public ModelAndView listHotel() {
		ModelAndView mav = new ModelAndView("hotel/listHotel");
		return mav;
	}
	
	/**
	 * 跳转至英雄管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listHero")
	@GetMapping("/listHero")
	@LogAnnotation(desc = "跳转至英雄管理页面")
	public ModelAndView listHero() {
		ModelAndView mav = new ModelAndView("hero/listHero");
		return mav;
	}
	
	/**
	 * 跳转至日志管理页面
	 * @return
	 */
	@PermissionAnnotation(permName = "listSystemLog")
	@GetMapping("/listSystemLog")
	@LogAnnotation(desc = "跳转至日志管理页面")
	public ModelAndView listSystemLog() {
		ModelAndView mav = new ModelAndView("systemLog/listSystemLog");
		return mav;
	}
	
}
