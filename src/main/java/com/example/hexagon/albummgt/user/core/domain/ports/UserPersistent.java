package com.example.hexagon.albummgt.user.core.domain.ports;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;

import java.util.List;
import java.util.Optional;

public interface UserPersistent {
    Optional<UserAggregate> findById(String id);

    Long save(UserAggregate userAggregate);

    void delete(String id);

    Long update(UserAggregate userAggregate);

    List<UserAggregate> findAll();
}
