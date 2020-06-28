package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Hero;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: HeroService.java
 * @Description: 英雄业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:11:41
 */
public interface HeroService {
	List<Hero> list(Map<String, String> paramMap);
	Object add(Hero hero);
	Object delete(int id);
	Object update(Hero hero);
	Hero get(int id);
}
