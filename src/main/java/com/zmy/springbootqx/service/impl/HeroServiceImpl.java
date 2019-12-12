package com.zmy.springbootqx.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.HeroMapper;
import com.zmy.springbootqx.pojo.Hero;
import com.zmy.springbootqx.service.HeroService;

@Service
public class HeroServiceImpl implements HeroService {
	@Autowired HeroMapper heroMapper;
	
	@Override
	public List<Hero> list(Map<String, String> paramMap) {
		return heroMapper.list(paramMap);
	}

	@Override
	public void add(Hero hero) {
		heroMapper.add(hero);
	}

	@Override
	public void delete(int id) {
		heroMapper.delete(id);
	}

	@Override
	public void update(Hero hero) {
		heroMapper.update(hero);
	}

	@Override
	public Hero get(int id) {
		return heroMapper.get(id);
	}

}
