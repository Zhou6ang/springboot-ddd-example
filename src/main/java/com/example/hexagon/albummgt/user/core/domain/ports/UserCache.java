package com.example.hexagon.albummgt.user.core.domain.ports;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import java.util.List;

public interface UserCache {

  UserAggregate get(String id);

  List<UserAggregate> getAll();

  String put(UserAggregate userAggregate);

  void delete(String id);

}
