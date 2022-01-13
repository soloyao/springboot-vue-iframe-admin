package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.User;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: PermissionService.java
 * @Description: 权限业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:11:59
 */
public interface PermissionService {
	int exist(Permission permission);
	List<Permission> list(Map<String, String> paramMap);
	Set<String> listPermStringSetsByUser(User user);
	Permission get(int id);
	void delete(int id);
	void add(Permission permission);
	void update(Permission permission);
	JSONObject listByUser(User user);
	List<Permission> listParentPermissions();
	List<Permission> listPermissions();
}
