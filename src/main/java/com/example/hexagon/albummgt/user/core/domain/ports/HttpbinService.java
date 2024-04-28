package com.example.hexagon.albummgt.user.core.domain.ports;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface HttpbinService {

  @GetExchange(url = "/get")
  ResponseEntity<Object> httpbinGet(
      @RequestHeader HttpHeaders headers, @RequestParam MultiValueMap<String, String> params);

  @PostExchange(url = "/post", contentType = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Object> httpbinPost(
      @RequestHeader HttpHeaders headers,
      @RequestParam MultiValueMap<String, String> params,
      @RequestBody Object body);
}
