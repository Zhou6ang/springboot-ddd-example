package com.example.hexagon.albummgt.user.driven.config;

import com.example.hexagon.albummgt.user.core.ApplicationEchoService;
import com.example.hexagon.albummgt.user.core.ApplicationUserService;
import com.example.hexagon.albummgt.user.core.domain.ports.HttpbinService;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistent;
import com.example.hexagon.albummgt.user.core.domain.service.DomainUserService;
import com.example.hexagon.albummgt.user.driven.persistent.DefaultUserPersistentAdapter;
import com.example.hexagon.albummgt.user.driven.persistent.UserRepository;
import com.example.hexagon.albummgt.user.driven.persistent.WishItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration("UserSpringConfig")
public class SpringBeanConfig {

  @Bean
  UserPersistent userPersistent(
      UserRepository userRepository, WishItemRepository wishItemRepository) {
    return new DefaultUserPersistentAdapter(userRepository, wishItemRepository);
  }

  @Bean
  DomainUserService domainUserService(UserPersistent userPersistent) {
    return new DomainUserService(userPersistent);
  }

  @Bean
  ApplicationUserService applicationUserService(DomainUserService domainUserService) {
    return new ApplicationUserService(domainUserService);
  }

  @Bean
  ApplicationEchoService applicationEchoService(
      @Qualifier("echoRestClient") RestClient restClient, HttpbinService httpbinService) {
    return new ApplicationEchoService(restClient, httpbinService);
  }
}
