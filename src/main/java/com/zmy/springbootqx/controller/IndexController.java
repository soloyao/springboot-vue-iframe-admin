package com.zmy.springbootqx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.annotation.PermissionAnnotation;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class IndexController {
	@GetMapping("/login")
	@LogAnnotation(desc = "跳转至登录页面")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login/login2");
		return mav;
	}
	
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home/home");
		return mav;
	}
	
	@GetMapping("/index")
	@LogAnnotation(desc = "跳转至首页")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index/index");
		return mav;
	}
	
	@PermissionAnnotation(permName = "druidServer")
	@GetMapping("/druidServer")
	@LogAnnotation(desc = "跳转至数据监控页面")
	public ModelAndView druidServer() {
		ModelAndView mav = new ModelAndView("druid/druidServer");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listCategory")
	@GetMapping("/listCategory")
	@LogAnnotation(desc = "跳转至分类管理页面")
	public ModelAndView listCategory() {
		ModelAndView mav = new ModelAndView("category/listCategory");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listUser")
	@GetMapping("/listUser")
	@LogAnnotation(desc = "跳转至用户管理页面")
	public ModelAndView listUser() {
		ModelAndView mav = new ModelAndView("user/listUser");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listRole")
	@GetMapping("/listRole")
	@LogAnnotation(desc = "跳转至角色管理页面")
	public ModelAndView listRole() {
		ModelAndView mav = new ModelAndView("role/listRole");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listPermission")
	@GetMapping("/listPermission")
	@LogAnnotation(desc = "跳转至菜单管理页面")
	public ModelAndView listPermission() {
		ModelAndView mav = new ModelAndView("permission/listPermission");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listHotel")
	@GetMapping("/listHotel")
	@LogAnnotation(desc = "跳转至旅馆管理页面")
	public ModelAndView listHotel() {
		ModelAndView mav = new ModelAndView("hotel/listHotel");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listHero")
	@GetMapping("/listHero")
	@LogAnnotation(desc = "跳转至英雄管理页面")
	public ModelAndView listHero() {
		ModelAndView mav = new ModelAndView("hero/listHero");
		return mav;
	}
	
	@PermissionAnnotation(permName = "listSystemLog")
	@GetMapping("/listSystemLog")
	@LogAnnotation(desc = "跳转至日志管理页面")
	public ModelAndView listSystemLog() {
		ModelAndView mav = new ModelAndView("systemLog/listSystemLog");
		return mav;
	}
	
}
