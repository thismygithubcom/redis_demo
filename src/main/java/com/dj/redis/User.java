package com.dj.redis;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
	private int id;
	private String name;
	private int age;
	private String remark;
	public static String getKeyName(int id) {
		return "User:"+id;
	}
}
