package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity(name = "users")
@Builder
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String name;
  private String email;
  private String gender;
  private String phone;
  @Column(name = "created_date")
  private Timestamp createdDate;
  @Column(name = "updated_date")
  private Timestamp updatedDate;

  public UserEntity() {
  }

  public static UserEntity toUserEntity(UserAggregate user) {
    return UserEntity.builder()
        .id(user.getId() != null ? Long.parseLong(user.getId()) : 0)
        .name(user.getName())
        .email(user.getEmail())
        .gender(user.getGender())
        .phone(user.getPhone())
        .updatedDate(user.getUpdateDate() == null ? Timestamp.valueOf(LocalDateTime.now())
            : user.getUpdateDate())
        .build();
  }

  public static UserAggregate toUserAggregate(UserEntity user) {
    return UserAggregate.builder().id(user.getId() + "")
        .name(user.getName())
        .gender(user.getGender())
        .email(user.getEmail())
        .phone(user.getPhone())
        .updateDate(user.getUpdatedDate())
        .build();
  }
}
