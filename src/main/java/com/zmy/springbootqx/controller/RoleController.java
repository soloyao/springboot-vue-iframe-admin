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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zmy.springbootqx.annotation.LogAnnotation;
import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.service.PermissionService;
import com.zmy.springbootqx.service.RoleService;

@RestController
public class RoleController {
	@Autowired RoleService roleService;
	@Autowired PermissionService permissionService;
	JSONObject json = new JSONObject();
	
	@GetMapping("/roles")
	@LogAnnotation(desc = "分页获取所有角色")
	public PageInfo<Role> list(@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageHelper.startPage(start, size, "id desc");
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!StringUtils.isEmpty(keyword)) {
			paramMap.put("keyword", keyword);
		}
		List<Role> rs = roleService.list(paramMap);
		PageInfo<Role> page = new PageInfo<Role>(rs, 5);
		return page;
	}
	
	@GetMapping("/roles/{id}")
	@LogAnnotation(desc = "获取单个角色")
	public String get(@PathVariable("id") int id) {
		Role role = roleService.get(id);
		List<Permission> permissions = permissionService.list(null);
		json.put("role", role);
		json.put("permissions", permissions);
		return json.toJSONString();
	}
	
	@GetMapping("/listPermissions")
	public List<Permission> get() {
		List<Permission> permissions = permissionService.listPermissions();
		return permissions;
	}
	
	@PostMapping("/roles")
	@LogAnnotation(desc = "新增角色")
	public String add(@RequestBody Role role) {
		int exist = roleService.exist(role);
		if (0 != exist) {
			json.put("code", 1);
			json.put("msg", "角色名已存在");
		} else {
			roleService.add(role);
			json.put("code", 0);
			json.put("msg", "新增成功");
		}
		return json.toJSONString();
	}
	
	@PutMapping("/roles")
	@LogAnnotation(desc = "修改角色")
	public String update(@RequestBody Role role) {
		roleService.update(role);
		return "success";
	}
	
	@DeleteMapping("/roles/{id}")
	@LogAnnotation(desc = "删除角色")
	public String delete(@PathVariable("id") int id) {
		roleService.delete(id);
		return "success";
	}
}
