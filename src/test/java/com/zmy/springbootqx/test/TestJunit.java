package com.zmy.springbootqx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmy.springbootqx.QxApplication;
import com.zmy.springbootqx.mapper.PermissionMapper;
import com.zmy.springbootqx.mapper.UserMapper;
import com.zmy.springbootqx.pojo.User;
import com.zmy.springbootqx.service.PermissionService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = QxApplication.class)
public class TestJunit {
	@Autowired UserMapper userMapper;
	@Autowired PermissionMapper permissionMapper;
	@Autowired PermissionService permissionService;
	
	@Test
	public void test() {
//		User user = new User();
//		user.setId(1);
//		JSONArray jsonArray = permissionService.listByUser(user);
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONObject jsonObj = jsonArray.getJSONObject(i);
//			System.out.println("\t" + jsonObj.get("name"));
//			JSONArray arr = jsonObj.getJSONArray("children");
//			for (int j = 0; j < arr.size(); j++) {
//				JSONObject obj = arr.getJSONObject(j);
//				System.out.println(obj.get("name"));
//			}
//		}
	}
	
}
