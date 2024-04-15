package com.example.hexagon.albummgt.user.driving.rest;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.core.ApplicationUserService;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User API")
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1", contact = @Contact(name = "Gang", email = "gang@example.com")))
public class UserController {

    private final ApplicationUserService applicationUserService;

    public UserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public ResponseMsg getUserById(@PathVariable("userId") String userId) {
        UserDTO result = applicationUserService.getUser(userId);
        return ResponseMsg.succ("get user successfully", result);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all users successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("")
    public ResponseMsg getAllUser() {
        return ResponseMsg.succ("get all users successfully", applicationUserService.getAllUsers());
    }

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add a new user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("")
    public ResponseMsg createUser(@RequestBody UserDTO request) {
        Long userId = applicationUserService.createUser(request);
        return ResponseMsg.succ("Add a new user successfully", UserDTO.builder().id(userId.toString()).build());
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("")
    public ResponseMsg updateUser(@RequestBody UserDTO request) {
        applicationUserService.updateUser(request);
        return ResponseMsg.succ("update user success, id=" + request.getId());
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a user successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input",content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    public ResponseMsg delete(@PathVariable("userId") String userId) {
        applicationUserService.deleteUser(userId);
        return ResponseMsg.succ("delete user success, id=" + userId);
    }
}