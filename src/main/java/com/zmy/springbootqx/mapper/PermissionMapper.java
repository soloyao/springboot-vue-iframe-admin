package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.User;

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
