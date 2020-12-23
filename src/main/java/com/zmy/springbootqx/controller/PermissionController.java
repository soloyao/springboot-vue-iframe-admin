package com.zmy.springbootqx.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.PermissionService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: PermissionController.java
 * @Description: 权限控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:06:48
 */
@ApiIgnore
@RestController
public class PermissionController {
	@Autowired PermissionService permissionService;
	JSONObject json = new JSONObject();
	
	/**
	 * 根据用户获取对应菜单
	 * @param session session对象
	 * @return 菜单集合
	 */
	@GetMapping("/permissionsByUser")
	@LogAnnotation(desc = "根据用户获取对应菜单")
	public JSONArray getPermissionsByUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return permissionService.listByUser(user);
	}
	
	/**
	 * 获取所有的父菜单
	 * @return 父菜单集合
	 */
	@GetMapping("/parentPermissions")
	@LogAnnotation(desc = "获取所有的父菜单")
	public List<Permission> list() {
		return permissionService.listParentPermissions();
	}
	
	/**
	 * 分页获取所有菜单
	 * @param start
	 * @param size
	 * @param keyword
	 * @param pid
	 * @return 分页对象
	 */
	@GetMapping("/permissions")
	@LogAnnotation(desc = "分页获取所有菜单")
	public PageInfo<Permission> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "pid") String pid) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("pid", pid);
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<Permission> rs = permissionService.list(paramMap);
		PageInfo<Permission> page = new PageInfo<Permission>(rs, 5);
		return page;
	}
	
	/**
	 * 获取单个菜单
	 * @param id
	 * @return 菜单对象
	 */
	@GetMapping("/permissions/{id}")
	@LogAnnotation(desc = "获取单个菜单")
	public Permission get(@PathVariable("id") int id) {
		Permission permission = permissionService.get(id);
		return permission;
	}
	
	/**
	 * 新增菜单
	 * @param permission
	 * @return 
	 */
	@PostMapping("/permissions")
	@LogAnnotation(desc = "新增菜单")
	public String add(@RequestBody Permission permission) {
		int exist = permissionService.exist(permission);
		if (0 != exist) {
			json.put("code", 1);
			json.put("msg", "菜单名已存在");
		} else {
			permissionService.add(permission);
			json.put("code", 0);
			json.put("msg", "新增成功");
		}
		return json.toJSONString();
	}
	
	/**
	 * 修改菜单
	 * @param permission
	 * @return
	 */
	@PutMapping("/permissions")
	@LogAnnotation(desc = "修改菜单")
	public String update(@RequestBody Permission permission) {
		permissionService.update(permission);
		return "success";
	}
	
	/**
	 * 删除菜单
	 * @param id
	 * @return
	 */
	@DeleteMapping("/permissions/{id}")
	@LogAnnotation(desc = "删除菜单")
	public String delete(@PathVariable("id") int id) {
		permissionService.delete(id);
		return "success";
	}
}
