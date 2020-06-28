package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.pojo.RolePermission;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: RoleMapper.java
 * @Description: 角色数据访问层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:08:39
 */
@Mapper
public interface RoleMapper {
	int exist(Role role);
	List<Role> list(Map<String, String> paramMap);
	Role get(int id);
	void delete(int id);
	void deletePermissionByRoleId(int id);
	void deleteUserByRoleId(int id);
	void add(Role role);
	void update(Role role);
	void addPermissionByRoleId(RolePermission rolePermission);
}
