package com.example.hexagon.albummgt.user.driving.dto;

import com.example.hexagon.albummgt.common.request.BaseRequest;
import java.util.Set;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserRequest extends BaseRequest {
  private Set<Long> ids;
  private String name;
  private String gender;
  private String email;
  private String phone;
  private String wishName;
  private String wishSinger;
  private String wishReleaseTime;
  private Long wishUserId;
  private String city;
}
