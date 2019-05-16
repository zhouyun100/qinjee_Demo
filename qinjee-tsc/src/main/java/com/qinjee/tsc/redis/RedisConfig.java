package com.qinjee.tsc.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置信息注入，启用JedisPool连接池
 *
 * @author 周赟
 *
 * @version 
 *
 * @since 2019年5月15日
 */
@Component
@PropertySource("classpath:config/redis.properties")
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

	private String host;
	
    private int port;
    
    private int timeout;
    
    private String password;
    
    private int database;
    
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }
    
    /**
     * @Bean注解将一个配置类的方法的返回值定义为一个bean，注册到spring里面
     * @return
     */
    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = getRedisConfig();
        JedisPool pool = new JedisPool(config,host,port,timeout,password,database);
        return pool;
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

}
