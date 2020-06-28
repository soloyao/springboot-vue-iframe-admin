package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zmy.springbootqx.pojo.Role;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: RoleService.java
 * @Description: 角色业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:12:05
 */
public interface RoleService {
	int exist(Role role);
	List<Role> list(Map<String, String> paramMap);
	Role get(int id);
	void delete(int id);
	void add(Role role);
	void update(Role role);
	void updateBatch(Set<String> roleIds, Set<String> permissionIds);
}
