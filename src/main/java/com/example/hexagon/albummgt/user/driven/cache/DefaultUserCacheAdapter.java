package com.example.hexagon.albummgt.user.driven.cache;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.ports.UserCache;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@AllArgsConstructor
public class DefaultUserCacheAdapter implements UserCache {

  private RedisTemplate redisTemplate;
  private static final String USER_CACHE_KEY = "user:hashmap";
  private static final String USER_LOCK_KEY = "user:lock";
  private RedissonClient redissonClient;

  @Override
  public UserAggregate get(String id) {
    return (UserAggregate) redisTemplate.opsForHash().get(USER_CACHE_KEY, id);
    //    return (UserAggregate) redisTemplate.opsForValue().get(id);
  }

  @Override
  public List<UserAggregate> getAll() {
    RLock lock = redissonClient.getLock(USER_LOCK_KEY);
    try {
      if (lock.tryLock(0,60, TimeUnit.SECONDS)) {
        log.info("get lock successfully.");
        Thread.sleep(30000);
      } else {
        log.warn("get lock failed.");
      }
    } catch (Exception e) {
      log.error("lock error:", e);
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
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
