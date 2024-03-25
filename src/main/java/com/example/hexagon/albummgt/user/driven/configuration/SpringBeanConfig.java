package com.example.hexagon.albummgt.user.driven.configuration;

import com.example.hexagon.albummgt.user.core.ApplicationUserService;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistentInterface;
import com.example.hexagon.albummgt.user.core.domain.service.DomainUserService;
import com.example.hexagon.albummgt.user.driven.persistent.UserPersistentAdapter;
import com.example.hexagon.albummgt.user.driven.persistent.UserRepository;
import com.example.hexagon.albummgt.user.driven.persistent.WishItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("UserSpringConfig")
public class SpringBeanConfig {

    @Bean
    UserPersistentInterface userPersistent(UserRepository userRepository, WishItemRepository wishItemRepository) {
        return new UserPersistentAdapter(userRepository,wishItemRepository);
    }

    @Bean
    DomainUserService domainUserService(UserPersistentInterface userPersistent) {
        return new DomainUserService(userPersistent);
    }

    @Bean
    ApplicationUserService applicationUserService(DomainUserService domainUserService) {
        return new ApplicationUserService(domainUserService);
    }
}
