package com.example.hexagon.albummgt.user.driving.rest;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.example.hexagon.albummgt.user.core.ApplicationEchoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Echo", description = "Echo API")
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
      @RequestParam("url") String url,
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
      @RequestParam("url") String url,
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params,
      @RequestBody(required = false) Object body) {
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
      @RequestParam("url") String url,
      @RequestHeader HttpHeaders headers,
      @Schema(hidden = true) @RequestParam MultiValueMap<String, String> params) {
    params.remove("url");
    Object result = applicationEchoService.echoDelete(url, headers, params);
    return ResponseMsg.success("execute DELETE successfully", result);
  }
}