package com.example.hexagon.albummgt.user.service;

import com.example.hexagon.albummgt.user.exception.DomainUserException;
import com.example.hexagon.albummgt.user.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class DefaultApplicationUserService implements ApplicationUserService{

    private final DefaultDomainUserService domainUserService;

    public Long createUser(UserDTO req) {
        log.info("create user in application service");
        return domainUserService.createUser(req);
    }

    public void updateUser(UserDTO req) {
        log.info("update user in application service");
        if (StringUtils.isEmpty(req.getId())) {
            log.error("user id is empty");
            throw new DomainUserException("user id is empty");
        }
        domainUserService.updateUser(req);
    }

    public UserDTO getUser(String userId) {
        log.info("get user in application service");
        if (StringUtils.isEmpty(userId)) {
            log.error("user id is empty");
            throw new DomainUserException("user id is empty");
        }
        UserAggregate user = domainUserService.getUserById(userId);
        return UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).phone(user.getPhone()).gender(user.getGender()).wishlists(user.getWishlists()).build();
    }

    public List<UserDTO> getAllUsers() {
        log.info("get all user in application service");
        List<UserAggregate> list = domainUserService.findAllUser();
        return list.stream().map(user->UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .gender(user.getGender())
                        .wishlists(user.getWishlists())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteUser(String userId) {
        log.info("delete user in application service");
        if (StringUtils.isEmpty(userId)) {
            log.error("user id is empty");
            throw new DomainUserException("user id is empty");
        }
        domainUserService.deleteUserById(userId);
    }

    public void addWishlist(UserDTO req) {
        log.info("add wishlist in application service");
        if (StringUtils.isEmpty(req.getId())) {
            log.error("user id is empty");
            throw new DomainUserException("user id is empty");
        }

        if (CollectionUtils.isEmpty(req.getWishlists())) {
            log.error("wishlist is empty");
            throw new DomainUserException("wishlist is empty");
        }
        domainUserService.addWishlist(req.getId(), req.getWishlists());
    }

}
