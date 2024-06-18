package com.example.hexagon.albummgt.user.driving.rest;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.core.ApplicationUserService;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/cache")
@Tag(name = "Cache", description = "Test with RedisTemplate + Redisson as cache")
public class UserCacheController {

  private final ApplicationUserService applicationUserService;

  public UserCacheController(ApplicationUserService applicationUserService) {
    this.applicationUserService = applicationUserService;
  }

  @Operation(summary = "Get a user from Cache by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get user successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/{userId}")
  public ResponseMsg getUserById(@PathVariable("userId") String userId) {
    UserDTO result = applicationUserService.getUserFromCache(userId);
    return ResponseMsg.success("get user from cache successfully", result);
  }

  @Operation(summary = "Get all users from cache")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Get all users successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseMsg getAllUser() {
    return ResponseMsg.success(
        "get all users from cache successfully", applicationUserService.getAllUsersFromCache());
  }

  @Operation(summary = "Add a new user to Redis Cache")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Add a new user successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping
  public ResponseMsg cacheUser(@RequestBody UserDTO request) {
    String userId = applicationUserService.addUserToCache(request);
    return ResponseMsg.success(
        "Add a new user to cache successfully", UserDTO.builder().id(userId).build());
  }

  @Operation(summary = "Delete a user from cache")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Delete a user successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping("/{userId}")
  public ResponseMsg delete(@PathVariable("userId") String userId) {
    applicationUserService.deleteUserFromCache(userId);
    return ResponseMsg.success("delete user from cache successfully, id=" + userId);
  }
}
