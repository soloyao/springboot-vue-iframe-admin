package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.HeroMapper;
import com.zmy.springbootqx.pojo.Hero;
import com.zmy.springbootqx.service.HeroService;
import com.zmy.springbootqx.util.Result;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: HeroServiceImpl.java
 * @Description: 英雄业务接口实现类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:12:43
 */
@Service
public class HeroServiceImpl implements HeroService {
	@Autowired HeroMapper heroMapper;
	
	@Override
	public List<Hero> list(Map<String, String> paramMap) {
		return heroMapper.list(paramMap);
	}

	@Override
	public Object add(Hero hero) {
		heroMapper.add(hero);
		return Result.success();
	}

	@Override
	public Object delete(int id) {
		heroMapper.delete(id);
		return Result.success();
	}

	@Override
	public Object update(Hero hero) {
		heroMapper.update(hero);
		return Result.success();
	}

	@Override
	public Hero get(int id) {
		return heroMapper.get(id);
	}

	@Override
	public List<Hero> listByIds(String[] heroIds) {
		return heroMapper.listByIds(heroIds);
	}

	@Override
	public String addForeach(List<Hero> heros) {
		int success_number = heroMapper.addForeach(heros);
		return success_number > 0 ? "success" : "error";
	}

}
