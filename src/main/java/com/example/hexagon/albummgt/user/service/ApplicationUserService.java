package com.example.hexagon.albummgt.user.service;

import com.example.hexagon.albummgt.user.presentation.dto.UserDTO;

import java.util.List;

public interface ApplicationUserService {
    Long createUser(UserDTO req);

    void updateUser(UserDTO req);

    UserDTO getUser(String userId);

    List<UserDTO> getAllUsers();

    void deleteUser(String userId);

    void addWishlist(UserDTO req);
}
