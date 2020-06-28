package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.UserMapper;
import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.pojo.UserRole;
import com.zmy.springbootqx.service.UserService;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UserServiceImpl.java
 * @Description: 用户业务接口实现类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:13:39
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired UserMapper userMapper;
	
	@Override
	public User login(User user) {
		return userMapper.login(user);
	}

	@Override
	public int exist(User user) {
		return userMapper.exist(user);
	}

	@Override
	public List<User> list(Map<String, String> paramMap) {
		return userMapper.list(paramMap);
	}

	@Override
	public void add(User user) {
		userMapper.add(user);
		if (null != user.getRoles()) {
			for (Role role : user.getRoles()) {
				UserRole userRole = new UserRole();
				userRole.setRid(role.getId());
				userRole.setUid(user.getId());
				userMapper.addRoleByUserId(userRole);
			}
		}
	}

	@Override
	public void update(User user) {
		//删除当前用户角色
		userMapper.deleteRoleByUserId(user.getId());
		//修改用户对应角色
		if (null != user.getRoles()) {
			for (Role role : user.getRoles()) {
				UserRole userRole = new UserRole();
				userRole.setRid(role.getId());
				userRole.setUid(user.getId());
				userMapper.addRoleByUserId(userRole);
			}
		}
		//修改用户基础信息
		userMapper.update(user);
	}
	
	@Override
	public void updateBatch(Set<String> userIds, Set<String> roleIds) {
		//删除当前用户关联角色
		for (String userId : userIds) {
			userMapper.deleteRoleByUserId(Integer.parseInt(userId));
		}
		//修改用户关联角色
		if (null != roleIds) {//为空则表示去掉用户所有角色
			for (String userId : userIds) {
				for (String roleId : roleIds) {
					UserRole userRole = new UserRole();
					userRole.setRid(Integer.parseInt(roleId));
					userRole.setUid(Integer.parseInt(userId));
					userMapper.addRoleByUserId(userRole);
				}
			}
		}
	}

	@Override
	public void delete(int id) {
		userMapper.delete(id);//删除用户
		userMapper.deleteRoleByUserId(id);//同时删除用户对应的角色
	}

	@Override
	public User get(int id) {
		return userMapper.get(id);
	}

}
