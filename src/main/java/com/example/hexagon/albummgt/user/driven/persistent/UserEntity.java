package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@DynamicUpdate
@Data
@Entity(name = "users")
@Builder
@AllArgsConstructor
@ToString(exclude = {"wishItem", "address"})
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String email;
  private String gender;
  private String phone;

  @Column(name = "created_date", insertable = false, updatable = false)
  private Timestamp createdDate;

  @Column(name = "updated_date", insertable = false, updatable = false)
  private Timestamp updatedDate;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  //    @BatchSize(size = 10) or using default_batch_fetch_size to relieve N+1 query problem in properties file.
  private List<WishItemEntity> wishItem;

  // mappedBy indicates current entity has not foreign key column for relationship, but other child entity has.
  @OneToOne(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @Fetch(FetchMode.JOIN) // using root.fetch() to avoid N+1 query problem.
  private AddressEntity address;

  public UserEntity() {}

  public static UserEntity toUserEntity(UserAggregate user) {
    return UserEntity.builder()
        .id(user.getId() == null ? 0 : Long.parseLong(user.getId()))
        .name(user.getName())
        .email(user.getEmail())
        .gender(user.getGender())
        .phone(user.getPhone())
        .build();
  }

  public static UserAggregate toUserAggregate(UserEntity user) {
    var userAggregate =
        UserAggregate.builder()
            .id(user.getId() + "")
            .name(user.getName())
            .gender(user.getGender())
            .email(user.getEmail())
            .phone(user.getPhone())
            .updateDate(user.getUpdatedDate())
            .address(AddressEntity.toAddress(user.getAddress()))
            .build();
    userAggregate.addWishlists(
        user.getWishItem() != null
            ? user.getWishItem().stream().map(WishItemEntity::toDTO).toList()
            : null);

    return userAggregate;
  }
}
