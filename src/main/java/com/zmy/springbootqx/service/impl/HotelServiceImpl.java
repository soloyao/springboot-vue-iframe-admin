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

@Service
public class HotelServiceImpl implements HotelService{
	@Autowired HotelMapper hotelMapper;
	
	@Override
	public List<Hotel> list(Map<String, String> paramMap) {
		return hotelMapper.list(paramMap);
	}

	@Override
	public void add(Hotel hotel) {
		hotel.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		hotelMapper.add(hotel);
	}

	@Override
	public void delete(int id) {
		hotelMapper.delete(id);
	}

	@Override
	public void update(Hotel hotel) {
		hotel.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		hotelMapper.update(hotel);
	}

	@Override
	public Hotel get(int id) {
		return hotelMapper.get(id);
	}
	
}
