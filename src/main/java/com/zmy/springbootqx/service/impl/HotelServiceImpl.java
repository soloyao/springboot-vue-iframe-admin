package com.zmy.springbootqx.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.springbootqx.mapper.HotelMapper;
import com.zmy.springbootqx.pojo.Hotel;
import com.zmy.springbootqx.service.HotelService;
import com.zmy.springbootqx.util.Result;

@Service
public class HotelServiceImpl implements HotelService{
	@Autowired HotelMapper hotelMapper;
	
	@Override
	public List<Hotel> list(Map<String, String> paramMap) {
		return hotelMapper.list(paramMap);
	}

	@Override
	public Object add(Hotel hotel) {
		hotel.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		hotelMapper.add(hotel);
		return Result.success();
	}

	@Override
	public Object delete(int id) {
		hotelMapper.delete(id);
		return Result.success();
	}

	@Override
	public Object update(Hotel hotel) {
		hotel.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		hotelMapper.update(hotel);
		return Result.success();
	}

	@Override
	public Hotel get(int id) {
		return hotelMapper.get(id);
	}
	
}
