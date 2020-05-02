package com.zmy.springbootqx.service;

import java.util.List;
import java.util.Map;

import com.zmy.springbootqx.pojo.Hotel;

public interface HotelService {
	List<Hotel> list(Map<String, String> paramMap);
	Object add(Hotel hotel);
	Object delete(int id);
	Object update(Hotel hotel);
	Hotel get(int id);
}
