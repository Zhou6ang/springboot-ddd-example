package com.example.hexagon.albummgt.user.driven.config;

import com.example.hexagon.albummgt.user.core.exception.EchoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@AllArgsConstructor
@Slf4j
public class RestClientConfig {

  private final RestClientProperties restClientProperties;

  @Bean
  RestClient echoRestClient() {
//    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(restClientProperties.getConnectionTimeout());
    requestFactory.setReadTimeout(restClientProperties.getReadTimeout());
    return RestClient.builder()
        .defaultStatusHandler(
            HttpStatusCode::isError,
            (req, res) -> {
              log.error("Error response: {}", res.getStatusCode());
              throw new EchoException("Client error: " + res.getStatusCode());
            })
        .requestFactory(requestFactory)
        .build();
  }
}
