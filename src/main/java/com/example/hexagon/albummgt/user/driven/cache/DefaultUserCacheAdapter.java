package com.example.hexagon.albummgt.user.driven.cache;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.ports.UserCache;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@AllArgsConstructor
public class DefaultUserCacheAdapter implements UserCache {

  private RedisTemplate redisTemplate;
  private final String USER_CACHE_KEY = "user:hashmap";

  @Override
  public UserAggregate get(String id) {
    return (UserAggregate) redisTemplate.opsForHash().get(USER_CACHE_KEY, id);
    //    return (UserAggregate) redisTemplate.opsForValue().get(id);
  }

  @Override
  public List<UserAggregate> getAll() {
    return new ArrayList<>(redisTemplate.opsForHash().entries(USER_CACHE_KEY).values());
  }

  @Override
  public String put(UserAggregate userAggregate) {
    String id = UUID.randomUUID().toString();
    userAggregate.setId(id);
    //    redisTemplate.opsForValue().set(userAggregate.getId(), userAggregate);
    redisTemplate.opsForHash().put(USER_CACHE_KEY, id, userAggregate);
    return id;
  }

  @Override
  public void delete(String id) {
//    redisTemplate.delete(id);
    redisTemplate.opsForHash().delete(USER_CACHE_KEY, id);
  }
}
