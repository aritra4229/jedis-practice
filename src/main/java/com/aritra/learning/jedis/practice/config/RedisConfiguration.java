package com.aritra.learning.jedis.practice.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.aritra.learning.jedis.practice.model.Developer;

@Configuration
public class RedisConfiguration {
	
	@Value("${redis.host}")
	private String host;
	@Value("${redis.password}")
	private String password;
	@Value("${redis.port}")
	private int port;
	
	@Value("${redis.jedis.pool.max-total}")
	private int maxTotal;
	@Value("${redis.jedis.pool.max-idle}")
	private int maxIdle;
	@Value("${redis.jedis.pool.min-idle}")
	private int minIdle;
	
	@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder = 
				(JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		return jedisPoolingClientConfigurationBuilder.poolConfig(poolConfig).build();
	}
	
	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName(host);
		if(StringUtils.isNotBlank(password)) {
			config.setPassword(RedisPassword.of(password));
		}
		config.setPort(port);
		return new JedisConnectionFactory(config, getJedisClientConfiguration());
	}
	
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(getJedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}
	
	@Bean
	@Qualifier("listOperations")
	public ListOperations<String, Developer> listOperations(RedisTemplate<String, Developer> redisTemplate) {
		return redisTemplate.opsForList();
	}
	
	@Bean
	@Qualifier("setOperations")
	public SetOperations<String, Developer> setOperations(RedisTemplate<String, Developer> redisTemplate) {
		return redisTemplate.opsForSet();
	}
	
	@Bean
	@Qualifier("hashOperations")
	//First String is for key of Redis and second is key of hash
	public HashOperations<String, String, Developer> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

}
