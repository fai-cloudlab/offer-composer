package com.fujitsu.cloudlab.offer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fujitsu.cloudlab.offer.json.model.Offer;

@Configuration
public class RedisConfig {

  @Value("${spring.cache.redis.host}")
  private String REDIS_HOSTNAME;

  @Value("${spring.cache.redis.port}")
  private Integer REDIS_PORT;
  
  @Value("${spring.cache.redis.password}")
  private String REDIS_PASSWORD;
  
  @Bean
  protected JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(REDIS_HOSTNAME, REDIS_PORT);
    //configuration.setPassword(RedisPassword.of(REDIS_PASSWORD));
    JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
    factory.afterPropertiesSet();
    return factory;
  }

  @Bean
  public RedisTemplate<String, Offer> redisTemplate() {
    final RedisTemplate<String, Offer> redisTemplate = new RedisTemplate<String, Offer>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }
  
}
