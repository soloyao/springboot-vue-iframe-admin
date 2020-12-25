package com.zmy.springbootqx.pojo;

/**
 * 
 * Copyright: Copyright (c) 2020 Mengyao Zeng
 *
 * @ClassName: Hero.java
 * @Description: 英雄实体类
 * 
 * @version: v1.0.0
 * @author: Mengyao Zeng
 * @email: 343722243@qq.com
 * @date: 2020年6月28日 上午11:10:09
 */
public class Hero {
	private int id;
	private String name;
	private Float hp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getHp() {
		return hp;
	}
	public void setHp(Float hp) {
		this.hp = hp;
	}
	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + ", hp=" + hp + "]";
	}
}
