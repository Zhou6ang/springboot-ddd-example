package com.example.hexagon.albummgt.user.core.domain.ports;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;

import com.example.hexagon.albummgt.user.driving.dto.UserRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserPersistent {

  Optional<UserAggregate> findById(String id);

  Long save(UserAggregate userAggregate);

  void delete(String id);

  Long update(UserAggregate userAggregate);

  Page<UserAggregate> findAll(UserRequest request);
}
