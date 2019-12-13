package com.zmy.springbootqx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class DemoController {
	
	@PostMapping("/demo")
	public JSONObject demo() {
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("name", "zmy");
		return json;
	}
}
