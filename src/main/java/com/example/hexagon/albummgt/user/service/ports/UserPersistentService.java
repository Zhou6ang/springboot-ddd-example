package com.example.hexagon.albummgt.user.service.ports;

import com.example.hexagon.albummgt.user.service.UserAggregate;

import java.util.List;
import java.util.Optional;

public interface UserPersistentService {
    Optional<UserAggregate> findById(String id);

    Long save(UserAggregate userAggregate);

    void delete(String id);

    Long update(UserAggregate userAggregate);

    List<UserAggregate> findAll();
}
