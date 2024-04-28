package com.example.hexagon.albummgt.user.core;

import com.example.hexagon.albummgt.user.core.domain.ports.HttpbinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
public class ApplicationEchoService {

  private final RestClient restClient;
  private final HttpbinService httpbinService;

  public Object echoGet(String url, HttpHeaders headers, MultiValueMap<String, String> params) {
    // remove Content-Length header if it exists
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    Object result =
        restClient
            .get()
            .uri(params.isEmpty() ? url : url + "?" + params.toSingleValueMap())
            .accept(MediaType.APPLICATION_JSON)
            .headers(header -> header.addAll(headers))
            .retrieve()
            .body(Object.class);
    log.info("echoGet result: {}", result);
    return result;
  }

  public Object echoPost(
      String url, HttpHeaders headers, MultiValueMap<String, String> params, Object body) {
    // remove Content-Length header if it exists
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    ResponseEntity result =
        restClient
            .post()
            .uri(params.isEmpty() ? url : url + "?" + params.toSingleValueMap())
            .accept(MediaType.APPLICATION_JSON)
            .headers(header -> header.addAll(headers))
            .body(body)
            .retrieve()
            .toEntity(Object.class);
    log.info("echoPost result: {}", result);
    return result.getBody();
  }

  public Object echoDelete(String url, HttpHeaders headers, MultiValueMap<String, String> params) {
    // remove Content-Length header if it exists
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    ResponseEntity result =
        restClient
            .delete()
            .uri(params.isEmpty() ? url : url + "?" + params.toSingleValueMap())
            .accept(MediaType.APPLICATION_JSON)
            .headers(header -> header.addAll(headers))
            .retrieve()
            .toEntity(Object.class);

    log.info("echoDelete result: {}", result);
    return result.getBody();
  }

  public Object httpBinGet(HttpHeaders headers, MultiValueMap<String, String> params) {
    // remove Content-Length header if it exists, otherwise an exception will be thrown.
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    ResponseEntity result = httpbinService.httpbinGet(headers,params);
    log.info("httpBinGet result: {}", result);
    return result.getBody();
  }

  public Object httpBinPost(HttpHeaders headers, MultiValueMap<String, String> params, Object body) {
    // remove Content-Length header if it exists, otherwise an exception will be thrown.
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    ResponseEntity result = httpbinService.httpbinPost(headers,params,body);
    log.info("httpBinPost result: {}", result);
    return result.getBody();
  }
}
