package com.wei.boot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisUtil {

    // ip
    private static String Host;

    // 端口号
    private static int Port;

    // 密码
    private static String Password;

    // 数据库
    private static int Database;

    // 超时时间
    private static int ConnectionTimeout;

    // 最大线程
    private static int MaxIdle;

    // 最小线程
    private static int MinIdle;

    // 最大总数
    private static int MaxTotal;

    private static boolean TestOnBorrow;

    @Value("${redis.host}")
    public void setHost(String host) {
        Host = host;
    }

    @Value("${redis.port}")
    public void setPort(int port) {
        Port = port;
    }

    @Value("${redis.password}")
    public void setPassword(String password) {
        Password = password;
    }

    @Value("${redis.database}")
    public void setDatabase(int database) {
        Database = database;
    }

    @Value("${redis.connectionTimeout}")
    public void setConnectionTimeout(int connectionTimeout) {
        ConnectionTimeout = connectionTimeout;
    }

    @Value("${redis.maxIdle}")
    public void setMaxIdle(int maxIdle) {
        MaxIdle = maxIdle;
    }

    @Value("${redis.minIdle}")
    public void setMinIdle(int minIdle) {
        MinIdle = minIdle;
    }

    @Value("${redis.maxTotal}")
    public void setMaxTotal(int maxTotal) {
        MaxTotal = maxTotal;
    }

    @Value("${redis.testOnBorrow}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        TestOnBorrow = testOnBorrow;
    }

    private static JedisPool jedisPool = null;

    /**
     * 从jedis连接池中获取获取jedis对象
     *
     * @return
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            jedisPool = init();
        }
        return jedisPool.getResource();
    }

    private static JedisPool init() {
        synchronized (JedisUtil.class) {
            if (jedisPool != null) {
                return jedisPool;
            }
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(MaxTotal);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(MaxIdle);
            config.setMinIdle(MinIdle);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(TestOnBorrow);

            // redis如果设置了密码：
            jedisPool = new JedisPool(config, Host, Port, ConnectionTimeout, Password, Database);
            return jedisPool;
        }
    }

    
    public static void main(String[] args) {
		Jedis jedis = new Jedis("47.97.100.29", 6379);
		jedis.auth("weisihua");
		jedis.set("name", "weisihua");
		System.out.println(jedis.get("name"));
	}
}
