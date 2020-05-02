package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Hero;

public interface HeroService {
	List<Hero> list(Map<String, String> paramMap);
	Object add(Hero hero);
	Object delete(int id);
	Object update(Hero hero);
	Hero get(int id);
}
