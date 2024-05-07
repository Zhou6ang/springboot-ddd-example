package com.example.hexagon.albummgt.user.core.domain.service;

import com.example.hexagon.albummgt.user.core.domain.ports.UserCache;
import com.example.hexagon.albummgt.user.core.exception.DomainUserException;
import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.WishItem;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistent;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DomainUserService {

  private final UserPersistent userPersistent;
  private final UserCache userCache;

  public Object createUser(UserDTO req, boolean cacheEnabled) {
    log.info("Create User in domain service");
    UserAggregate aggregate =
        UserAggregate.builder()
            .name(req.getName())
            .email(req.getEmail())
            .phone(req.getPhone())
            .gender(req.getGender())
            .build();
    aggregate.addWishlists(req.getWishlists());
    if (cacheEnabled) {
      return userCache.put(aggregate);
    }
    return userPersistent.save(aggregate);
  }

  public void addWishlist(String userId, List<WishItem> items) {
    log.info("add wishlist in domain service");
    UserAggregate userAggregate = getUserById(userId);
    userAggregate.addWishlists(items);
    userPersistent.save(userAggregate);
  }

  public void removeWishlist(String userId, List<Long> idList) {
    log.info("remove wishlist in domain service");
    UserAggregate userAggregate = getUserById(userId);
    userAggregate.removeWishlists(idList);
    userPersistent.save(userAggregate);
  }

  public Long updateUser(UserDTO req) {
    log.info("update user information in domain service");
    UserAggregate userAggregate = getUserById(req.getId());
    if (req.getEmail() != null) {
      userAggregate.setEmail(req.getEmail());
    }
    if (req.getName() != null) {
      userAggregate.setName(req.getName());
    }
    if (req.getPhone() != null) {
      userAggregate.setPhone(req.getPhone());
    }
    if (req.getGender() != null) {
      userAggregate.setGender(req.getGender());
    }
    if (req.getWishlists() != null) {
      userAggregate.getWishlists().clear();
      userAggregate.addWishlists(req.getWishlists());
    }
    return userPersistent.update(userAggregate);
  }

  public UserAggregate getUserById(String userId) {
    return getUserById(userId, false);
  }

  public UserAggregate getUserById(String userId, boolean fromCache) {
    log.info("get user information in domain service");
    if (fromCache) {
      return userCache.get(userId);
    }
    return userPersistent
        .findById(userId)
        .orElseThrow(() -> new DomainUserException("User with given id doesn't exist"));
  }

  public void deleteUserById(String userId, boolean fromCache) {
    log.info("delete user in domain service");
    if(fromCache){
      userCache.delete(userId);
    }else{
      userPersistent.delete(userId);
    }
  }

  public List<UserAggregate> findAllUser(boolean fromCache) {
    log.info("find all user in domain service");
    if (fromCache){
      return userCache.getAll();
    }
    return userPersistent.findAll();
  }
}
