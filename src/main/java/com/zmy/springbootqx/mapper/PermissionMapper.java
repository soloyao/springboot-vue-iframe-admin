package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.User;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: PermissionMapper.java
 * @Description: 权限数据访问层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:08:28
 */
@Mapper
public interface PermissionMapper {
	int exist(Permission permission);
	List<Permission> list(Map<String, String> paramMap);
	Permission get(int id);
	void delete(int id);
	void deleteRoleByPermissionId(int id);
	void deleteChildrenByParentId(int id);
	void add(Permission permission);
	void update(Permission permission);
	List<Permission> listByUser(User user);
	List<Permission> listParentPermissions();
	List<Permission> listPermissions();
}
