package com.example.hexagon.albummgt.user.core;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.service.DomainUserService;
import com.example.hexagon.albummgt.user.core.exception.DomainUserException;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import com.example.hexagon.albummgt.user.driving.dto.UserRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class ApplicationUserService {

  private final DomainUserService domainUserService;

  public Long createUser(UserDTO req) {
    log.info("create user in application service");
    return (Long) domainUserService.createUser(req, false);
  }

  public String addUserToCache(UserDTO req) {
    log.info("add user to cache in application service");
    return (String) domainUserService.createUser(req, true);
  }

  public void updateUser(UserDTO req) {
    log.info("update user in application service");
    checkValidUserId(req.getId());
    domainUserService.updateUser(req);
  }

  public UserDTO getUser(String userId) {
    return getUser(userId, false);
  }

  public UserDTO getUserFromCache(String userId) {
    return getUser(userId, true);
  }

  private UserDTO getUser(String userId, boolean fromCache) {
    log.info("get user in application service");
    checkValidUserId(userId);
    UserAggregate user = domainUserService.getUserById(userId, fromCache);
    return UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .phone(user.getPhone())
        .gender(user.getGender())
        .wishlists(user.getWishlists())
        .address(user.getAddress())
        .build();
  }

  public List<UserDTO> getAllUsersFromCache() {
    return Optional.ofNullable(domainUserService.findAllUserFromCache())
        .orElseThrow(() -> new DomainUserException("No user found in cache."))
        .stream()
        .map(
            x ->
                UserDTO.builder()
                    .id(x.getId())
                    .name(x.getName())
                    .email(x.getEmail())
                    .phone(x.getPhone())
                    .gender(x.getGender())
                    .wishlists(x.getWishlists())
                    .build())
        .toList();
  }

  public Page<UserDTO> getAllUsers(UserRequest req) {
    log.info("get all user in application service");
    Page<UserAggregate> list = domainUserService.findAllUser(req);
    return new PageImpl<>(
        list.stream()
            .map(
                user ->
                    UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .gender(user.getGender())
                        .wishlists(user.getWishlists())
                        .address(user.getAddress())
                        .build())
            .collect(Collectors.toList()),
        list.getPageable(),
        list.getTotalElements());
  }

  public void deleteUser(String userId) {
    log.info("delete user in application service");
    checkValidUserId(userId);
    domainUserService.deleteUserById(userId, false);
  }

  public void deleteUserFromCache(String userId) {
    log.info("delete user from cache in application service");
    checkValidUserId(userId);
    domainUserService.deleteUserById(userId, true);
  }

  public void addWishlist(UserDTO req) {
    log.info("add wishlist in application service");
    checkValidUserId(req.getId());
    if (CollectionUtils.isEmpty(req.getWishlists())) {
      log.error("wishlist is empty");
      throw new DomainUserException("wishlist is empty");
    }
    domainUserService.addWishlist(req.getId(), req.getWishlists());
  }

  private void checkValidUserId(String userId) {
    if (StringUtils.isEmpty(userId)) {
      log.error("user id is empty");
      throw new DomainUserException("user id is empty");
    }
  }
}
