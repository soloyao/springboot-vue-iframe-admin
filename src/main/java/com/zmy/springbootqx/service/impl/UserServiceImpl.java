package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.UserMapper;
import com.zmy.springbootqx.pojo.Role;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.pojo.UserRole;
import com.zmy.springbootqx.service.UserService;


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
	public void delete(int id) {
		userMapper.delete(id);//删除用户
		userMapper.deleteRoleByUserId(id);//同时删除用户对应的角色
	}

	@Override
	public User get(int id) {
		return userMapper.get(id);
	}

}
