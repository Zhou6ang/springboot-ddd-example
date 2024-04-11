package com.example.hexagon.albummgt.user.presentation.rest;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.service.ApplicationUserService;
import com.example.hexagon.albummgt.user.presentation.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ApplicationUserService applicationUserService;

    public UserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/{userId}")
    public ResponseMsg getUserById(@PathVariable("userId") String userId) {
        UserDTO result = applicationUserService.getUser(userId);
        return ResponseMsg.succ("get user successfully", result);
    }

    @GetMapping("")
    public ResponseMsg getAllUser() {
        return ResponseMsg.succ("get all users successfully", applicationUserService.getAllUsers());
    }

    @PostMapping("")
    public ResponseMsg createUser(@RequestBody UserDTO request) {
        Long userId = applicationUserService.createUser(request);
        return ResponseMsg.succ("", UserDTO.builder().id(userId.toString()).build());
    }

    @PutMapping("")
    public ResponseMsg updateUser(@RequestBody UserDTO request) {
        applicationUserService.updateUser(request);
        return ResponseMsg.succ("update user success, id=" + request.getId());
    }

    @DeleteMapping("/{userId}")
    public ResponseMsg delete(@PathVariable("userId") String userId) {
        applicationUserService.deleteUser(userId);
        return ResponseMsg.succ("delete user success, id=" + userId);
    }
}