package com.zmy.springbootqx.controller;

import java.security.NoSuchAlgorithmException;
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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.RoleService;
import com.zmy.springbootqx.service.UserService;

@RestController
public class UserController {
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	
	@PostMapping("/login")
	@LogAnnotation(desc = "登录")
	public String login(@RequestBody User user, HttpSession session) {
		User loginUser = userService.login(user);
		JSONObject json = new JSONObject();
		if (null == loginUser) {
			json.put("code", 1);
		} else {
			session.setAttribute("user", loginUser);
			json.put("user", loginUser);
			json.put("code", 0);
		}
		return json.toJSONString();
	}
	
	@GetMapping("/logout")
	@LogAnnotation(desc = "注销")
	public void logout(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {
			session.removeAttribute("user");
		}
	}
	
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
	
	@GetMapping("/listRoles")
	public List<Role> get() {
		List<Role> roles = roleService.list(null);
		return roles;
	}
	
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
	
	@PutMapping("/users")
	@LogAnnotation(desc = "修改用户")
	public String update(@RequestBody User user) throws NoSuchAlgorithmException {
		userService.update(user);
		return "success";
	}
	
	@DeleteMapping("/users/{id}")
	@LogAnnotation(desc = "删除用户")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "success";
	}
}
