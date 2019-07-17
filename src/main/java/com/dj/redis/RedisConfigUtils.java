package com.dj.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConfigUtils {
	private static JedisPool pool;
	static {
		JedisPoolConfig pollConfig = new JedisPoolConfig();
		pollConfig.setMaxTotal(5);
		pollConfig.setMaxIdle(1);
		pool = new JedisPool(pollConfig,"localhost",6379);
	}
	
	
	// 得到 redis
	public static Jedis getJedis () {
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		return jedis;
	}
	
	// 关闭 redis
	public static void close(Jedis jedis) {
		jedis.close();
	}
}
