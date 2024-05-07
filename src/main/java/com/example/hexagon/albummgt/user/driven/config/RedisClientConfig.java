package com.example.hexagon.albummgt.user.driven.config;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@AllArgsConstructor
@Slf4j
public class RedisClientConfig {

  //  @Bean
  //  JedisConnectionFactory jedisConnectionFactory() {
  //    return new JedisConnectionFactory();
  //  }
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    template.setKeySerializer(stringRedisSerializer);
    template.setHashKeySerializer(stringRedisSerializer);
    //Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(UserAggregate.class);
    GenericJackson2JsonRedisSerializer jsonRedisSerializer =
        new GenericJackson2JsonRedisSerializer();
    template.setValueSerializer(jsonRedisSerializer);
    template.setHashValueSerializer(jsonRedisSerializer);
    return template;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(factory);
    return template;
  }
}
