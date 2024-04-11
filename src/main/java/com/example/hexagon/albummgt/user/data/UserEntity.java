package com.example.hexagon.albummgt.user.data;

import com.example.hexagon.albummgt.user.service.UserAggregate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

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
    @Column(name="created_date")
    private Timestamp createdDate;
    @Column(name="updated_date")
    private Timestamp updatedDate;

    public UserEntity(){}
    public static UserEntity toUserEntity(UserAggregate user) {
        return UserEntity.builder()
                .id(user.getId() != null ? Long.parseLong(user.getId()):0)
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .phone(user.getPhone())
                .updatedDate(user.getUpdateDate() == null ? Timestamp.valueOf(LocalDateTime.now()): user.getUpdateDate())
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
