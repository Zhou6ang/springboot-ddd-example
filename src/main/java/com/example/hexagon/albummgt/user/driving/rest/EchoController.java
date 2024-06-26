package com.example.hexagon.albummgt.user.driving.rest;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.core.ApplicationEchoService;
import com.example.hexagon.albummgt.user.driving.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/echo")
@Tag(name = "Echo", description = "Test with RestClient")
// @SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "basic",name = "basicAuth", in =
// SecuritySchemeIn.HEADER)
@SecurityScheme(
    type = SecuritySchemeType.APIKEY,
    name = "Authorization",
    in = SecuritySchemeIn.HEADER)
public class EchoController {

  private final ApplicationEchoService applicationEchoService;

  public EchoController(ApplicationEchoService applicationEchoService) {
    this.applicationEchoService = applicationEchoService;
  }

  @Operation(summary = "Echo GET")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Echo GET successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Target url not found",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  public ResponseMsg echoGet(
      @Parameter(required = true, example = "https://localhost:8888/api/v1/users")
          @RequestParam("url")
          String url,
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params) {
    params.remove("url");
    Object result = applicationEchoService.echoGet(url, headers, params);
    return ResponseMsg.success("execute GET successfully", result);
  }

  @Operation(summary = "Echo POST")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "execute POST successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping()
  public ResponseMsg echoPost(
      @Parameter(example = "https://localhost:8888/api/v1/users") @RequestParam("url") String url,
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @RequestBody(required = false) UserDTO body) {
    params.remove("url");
    Object result = applicationEchoService.echoPost(url, headers, params, body);
    return ResponseMsg.success("execute POST successfully", result);
  }

  @Operation(summary = "Echo DELETE")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "execute DELETE successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Target url not found",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @DeleteMapping
  public ResponseMsg echoDelete(
      @Parameter(example = "https://localhost:8888/api/v1/users/xxx") @RequestParam("url")
          String url,
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params) {
    params.remove("url");
    Object result = applicationEchoService.echoDelete(url, headers, params);
    return ResponseMsg.success("execute DELETE successfully", result);
  }

  @Operation(summary = "Echo httpbin GET")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Echo httpbin GET successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Target url not found",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping("/httpbin")
  //  @SecurityRequirement(name = "basicAuth")
  @SecurityRequirement(name = "Authorization")
  public ResponseMsg httpBinGet(
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params) {
    log.info("auth: {}", auth);
    Object result = applicationEchoService.httpBinGet(headers, params);

    return ResponseMsg.success("execute httpbin GET successfully", result);
  }

  @Operation(summary = "Echo httpbin POST")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "execute httpbin POST successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @PostMapping("/httpbin")
  @SecurityRequirement(name = "basicAuth")
  public ResponseMsg httpBinPost(
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @Schema(hidden = true) @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
      @RequestBody Object body) {
    log.info("authorization: {}", auth);
    Object result = applicationEchoService.httpBinPost(headers, params, body);
    return ResponseMsg.success("execute POST successfully", result);
  }
}
