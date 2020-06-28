package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zmy.springbootqx.pojo.User;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: UserService.java
 * @Description: 用户业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:12:22
 */
public interface UserService {
	User login(User user);
	int exist(User user);
	List<User> list(Map<String, String> paramMap);
	void add(User user);
	void update(User user);
	void delete(int id);
	User get(int id);
	void updateBatch(Set<String> userIds, Set<String> roleIds);
}
