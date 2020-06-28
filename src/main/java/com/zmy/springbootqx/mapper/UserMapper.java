package com.zmy.springbootqx.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.pojo.UserRole;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UserMapper.java
 * @Description: 用户数据访问层
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:08:57
 */
@Mapper
public interface UserMapper {
	User login(User user);
	int exist(User user);
	List<User> list(Map<String, String> paramMap);
	void add(User user);
	void update(User user);
	void delete(int id);
	void deleteRoleByUserId(int id);
	User get(int id);
	void addRoleByUserId(UserRole userRole);
}
