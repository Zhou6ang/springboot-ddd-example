package com.example.hexagon.albummgt.user.service;

import com.example.hexagon.albummgt.user.presentation.dto.UserDTO;

import java.util.List;

public interface DomainUserService {
    Long createUser(UserDTO req);

    void addWishlist(String userId, List<WishItem> items);

    void removeWishlist(String userId, List<Long> idList);

    Long updateUser(UserDTO req);

    UserAggregate getUserById(String userId);

    void deleteUserById(String userId);

    List<UserAggregate> findAllUser();
}
