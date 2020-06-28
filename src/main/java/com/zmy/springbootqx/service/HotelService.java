package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Hotel;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: HotelService.java
 * @Description: 旅馆业务接口
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:11:51
 */
public interface HotelService {
	List<Hotel> list(Map<String, String> paramMap);
	Object add(Hotel hotel);
	Object delete(int id);
	Object update(Hotel hotel);
	Hotel get(int id);
}
