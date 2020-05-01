package com.zmy.springbootqx.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmy.springbootqx.mapper.PermissionMapper;
import com.zmy.springbootqx.pojo.Permission;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired PermissionMapper permissionMapper;

	@Override
	public List<Permission> list(Map<String, String> paramMap) {
		return permissionMapper.list(paramMap);
	}

	@Override
	public Permission get(int id) {
		return permissionMapper.get(id);
	}

	@Override
	public void delete(int id) {
		permissionMapper.delete(id);//删除本身
		permissionMapper.deleteRoleByPermissionId(id);//删除和菜单关联的角色
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("pid", String.valueOf(id));
		List<Permission> list = permissionMapper.list(paramMap);
		for (Permission permission : list) {
			permissionMapper.deleteRoleByPermissionId(permission.getId());
		}
		permissionMapper.deleteChildrenByParentId(id);//删除父菜单下的所有子菜单
	}

	@Override
	public void add(Permission permission) {
		permissionMapper.add(permission);
	}

	@Override
	public void update(Permission permission) {
		permissionMapper.update(permission);
	}

	@Override
	public int exist(Permission permission) {
		return permissionMapper.exist(permission);
	}

	@Override
	public Set<String> listPermStringSetsByUser(User user) {
		List<Permission> permissions = permissionMapper.listByUser(user);
		Set<String> permStringSets = new HashSet<String>();
		for (Permission permission : permissions) {
			if (!StringUtils.isEmpty(permission.getUrl())) {
				permStringSets.add(permission.getUrl());
			}
		}
		return permStringSets;
	}
	
	@Override
	public JSONArray listByUser(User user) {
		List<Permission> list = permissionMapper.listByUser(user);
		if (null != list && list.size() != 0) {
			JSONArray parentArr = new JSONArray();
			for (Permission parent : list) {
				if (0 == parent.getPid()) {
//					System.out.println(parent.getName());
					JSONObject parentObj = new JSONObject();
					parentObj.put("id", parent.getId());
					parentObj.put("name", parent.getName());
					parentObj.put("url", parent.getUrl());
					JSONArray childrenArr = new JSONArray();
					for (Permission children : list) {
						if (parent.getId() == children.getPid()) {
//							System.out.println("\t" + children.getName());
							JSONObject childrenObj = new JSONObject();
							childrenObj.put("id", children.getId());
							childrenObj.put("name", children.getName());
							childrenObj.put("url", children.getUrl());
							childrenArr.add(childrenObj);
						}
					}
					parentObj.put("children", childrenArr);
					parentArr.add(parentObj);
				}
			}
			return parentArr;
		} else {
			return null;
		}
	}

	@Override
	public List<Permission> listParentPermissions() {
		return permissionMapper.listParentPermissions();
	}

	@Override
	public List<Permission> listPermissions() {
		return permissionMapper.listPermissions();
	}

}
