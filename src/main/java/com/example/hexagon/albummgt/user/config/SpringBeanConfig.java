package com.example.hexagon.albummgt.user.config;

import com.example.hexagon.albummgt.user.service.ApplicationUserService;
import com.example.hexagon.albummgt.user.service.DefaultApplicationUserService;
import com.example.hexagon.albummgt.user.service.ports.UserPersistentService;
import com.example.hexagon.albummgt.user.service.DefaultDomainUserService;
import com.example.hexagon.albummgt.user.data.DefaultUserPersistentServiceAdapter;
import com.example.hexagon.albummgt.user.data.UserRepository;
import com.example.hexagon.albummgt.user.data.WishItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("UserSpringConfig")
public class SpringBeanConfig {

    @Bean
    UserPersistentService userPersistent(UserRepository userRepository, WishItemRepository wishItemRepository) {
        return new DefaultUserPersistentServiceAdapter(userRepository,wishItemRepository);
    }

    @Bean
    DefaultDomainUserService domainUserService(UserPersistentService userPersistent) {
        return new DefaultDomainUserService(userPersistent);
    }

    @Bean
    ApplicationUserService applicationUserService(DefaultDomainUserService domainUserService) {
        return new DefaultApplicationUserService(domainUserService);
    }
}
