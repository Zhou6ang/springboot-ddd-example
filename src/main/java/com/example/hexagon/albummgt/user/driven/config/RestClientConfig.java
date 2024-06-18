package com.example.hexagon.albummgt.user.driven.config;

import com.example.hexagon.albummgt.user.core.domain.ports.HttpbinService;
import com.example.hexagon.albummgt.user.core.exception.EchoException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@AllArgsConstructor
@Slf4j
public class RestClientConfig {

  private final RestClientProperties restClientProperties;

  @Bean
  RestClient echoRestClient(RestClientSsl restClientSsl) {
    //HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
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

  @Bean
  RestClient localhostRestClient(RestClientSsl restClientSsl) {
    //HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
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
        .apply(restClientSsl.fromBundle("myhttpshost"))
        .build();
  }

  @Bean
  HttpbinService httpbinRestClient() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(restClientProperties.getConnectionTimeout());
    requestFactory.setReadTimeout(restClientProperties.getReadTimeout());
    RestClient client =
        RestClient.builder()
            .baseUrl(restClientProperties.getBaseUrl())
            .defaultStatusHandler(
                HttpStatusCode::isError,
                (req, res) -> {
                  log.error("Error response for http bin: {}", res.getStatusCode());
                  throw new EchoException("Client error: " + res.getStatusCode());
                })
            .requestFactory(requestFactory)
            .build();
    RestClientAdapter adapter = RestClientAdapter.create(client);
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
    return factory.createClient(HttpbinService.class);
  }
}
