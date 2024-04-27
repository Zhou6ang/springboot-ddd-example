package com.example.hexagon.albummgt.user.driven.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rest.client")
@Configuration
@Data
public class RestClientProperties {
  private String baseUrl;
  private int connectionTimeout;
  private int readTimeout;

}
