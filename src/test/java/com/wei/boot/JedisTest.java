package com.wei.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wei.boot.util.JedisUtil;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTest {

	@Test
	public void testRedis() {
		Jedis jedis = JedisUtil.getJedis();
		jedis.set("name", "weisihua04678");
		System.out.println(jedis.get("name"));
	}
}
