package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Role;

public interface RoleService {
	int exist(Role role);
	List<Role> list(Map<String, String> paramMap);
	Role get(int id);
	void delete(int id);
	void add(Role role);
	void update(Role role);
}
