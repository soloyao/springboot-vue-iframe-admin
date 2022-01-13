package com.zmy.springbootqx.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.annotation.PermissionAnnotation;
import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.PermissionService;
import com.zmy.springbootqx.service.RoleService;
import com.zmy.springbootqx.service.UserService;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UserController.java
 * @Description: 用户管理控制层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:07:22
 */
@ApiIgnore
@RestController
public class UserController {
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	@Autowired PermissionService permissionService;
	
	/**
	 * 登录
	 * @param user
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	@LogAnnotation(desc = "登录")
	public String login(@RequestBody User user, HttpSession session) {
		String sessionCode = (String) session.getAttribute("VerifyCode");
		JSONObject json = new JSONObject();
		String code = user.getCode();
		if (!sessionCode.equalsIgnoreCase(code)) {
			json.put("code", 2);//验证码错误
			return json.toJSONString();
		}
		User loginUser = userService.login(user);
		if (null == loginUser) {
			json.put("code", 1);//用户名或密码错误
		} else {
			Set<String> perms = permissionService.listPermStringSetsByUser(loginUser);
			session.setAttribute("perms", perms);
			session.setAttribute("user", loginUser);
			json.put("user", loginUser);
			json.put("code", 0);
		}
		return json.toJSONString();
	}
	
	/**
	 * 注销
	 * @param session
	 */
	@GetMapping("/logout")
	@LogAnnotation(desc = "注销")
	public void logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {
			session.removeAttribute("user");
		}
	}
	
	/**
	 * 分页获取所有用户
	 * @param start
	 * @param size
	 * @param keyword
	 * @return
	 */
	@PermissionAnnotation(permName = "listUser")
	@GetMapping("/users")
	@LogAnnotation(desc = "分页获取所有用户")
	public PageInfo<User> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<User> us = userService.list(paramMap);
		PageInfo<User> page = new PageInfo<User>(us, 5);
		return page;
	}
	
	/**
	 * 获取单个用户
	 * @param id
	 * @return
	 */
	@PermissionAnnotation(permName = "listUser")
	@GetMapping("/users/{id}")
	@LogAnnotation(desc = "获取单个用户")
	public String get(@PathVariable("id") int id) {
		User user = userService.get(id);
		List<Role> roles = roleService.list(null);
		JSONObject json = new JSONObject();
		json.put("user", user);
		json.put("roles", roles);
		return json.toJSONString();
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	@PermissionAnnotation(permName = "listHero")
	@GetMapping("/listRoles")
	public List<Role> get() {
		List<Role> roles = roleService.list(null);
		return roles;
	}
	
	/**
	 * 批量分配用户角色
	 * @param params
	 * @return
	 */
	@PermissionAnnotation(permName = "batchPerUser")
	@PostMapping("/usersBatch")
	@LogAnnotation(desc = "批量分配用户角色")
	public String addBatch(@RequestBody JSONObject params) {
		String[] userStrs = params.get("userIds").toString().split(",");
		Set<String> userIds = new HashSet<String>();
		for (String userStr : userStrs) {
			userIds.add(userStr);
		}
		String[] roleStrs = params.get("roleIds").toString().split(",");
		Set<String> roleIds = new HashSet<String>();
		for (String roleStr : roleStrs) {
			roleIds.add(roleStr);
		}
		userService.updateBatch(userIds, roleIds);
		return "success";
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PermissionAnnotation(permName = "addUser")
	@PostMapping("/users")
	@LogAnnotation(desc = "新增用户")
	public String add(@RequestBody User user) throws NoSuchAlgorithmException {
		int exist = userService.exist(user);
		JSONObject json = new JSONObject();
		if (0 != exist) {
			json.put("code", 1);
			json.put("msg", "用户名已存在");
		} else {
			userService.add(user);
			json.put("code", 0);
			json.put("msg", "新增成功");
		}
		return json.toJSONString();
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PermissionAnnotation(permName = "updateUser")
	@PutMapping("/users")
	@LogAnnotation(desc = "修改用户")
	public String update(@RequestBody User user) throws NoSuchAlgorithmException {
		userService.update(user);
		return "success";
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@PermissionAnnotation(permName = "deleteUser")
	@DeleteMapping("/users/{id}")
	@LogAnnotation(desc = "删除用户")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "success";
	}
}
