package com.dj.redis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDemo {
	public static void main(String[] args) {
		HashTest(1);
	}



	private static Jedis getRedis() {
		String host = "localhost";
		int port = 6379;
		Jedis jedis = new Jedis(host,port);
		jedis.auth("123456");
		return jedis;
	}
	
	/** String
	 * 如果key存在就查询 redis ，如果不存在则查询数据库 
	 */
	private static String redisMysql(String key) {
		String result = "";
		Jedis jedis = RedisConfigUtils.getJedis();
		if (jedis.exists(key)) {
			result = jedis.get(key);
			System.out.println("redis中查询得到");
		} else {
			String value = "张三";
			jedis.set(key, value);
			result = value;
			System.out.println("数据库中查询得到");
		}
		RedisConfigUtils.close(jedis);
		return result;
	}
	/** hash
	 * 如果key存在就查询 redis ，如果不存在则查询数据库 
	 */
	public static void HashTest(int id) {
		Jedis jedis = RedisConfigUtils.getJedis();
		String key = User.getKeyName(id);
		if (jedis.exists(key)) {
			Map<String, String> map = jedis.hgetAll(key);
			User user = new User();
			user.setId(Integer.parseInt(map.get("id")));
			user.setName(map.get("name"));
			user.setAge(Integer.parseInt(map.get("age")));
			user.setRemark(map.get("remark"));
			System.out.println("redis 中查询获取:"+user);
		} else {
			User user = new User();
			user.setId(1);
			user.setAge(10);
			user.setName("张三");
			user.setRemark("学生");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", user.getId()+"");
			map.put("name",user.getName());
			map.put("age",user.getAge()+"");
			map.put("remark",user.getRemark());
			jedis.hmset(key, map);
			System.out.println("数据库中查询获取:"+user);
		}
		RedisConfigUtils.close(jedis);
	}
	
	private static void StringCmd() {
		Jedis jedis = getRedis();
//		jedis.set("name", "张三");
		String name = jedis.get("name");
		System.out.println(name);
		
		jedis.close();
	}
}
