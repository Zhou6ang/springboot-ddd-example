package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.WishItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Data
@Entity(name = "addresses")
@Builder
@AllArgsConstructor
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String street;
  private String city;
  private String county;
  @Column(name = "created_date")
  private Timestamp createdDate;
  @Column(name = "updated_date")
  private Timestamp updatedDate;

  public AddressEntity() {
  }

  public static AddressEntity fromUserAggregate(WishItem item) {
    return AddressEntity.builder()
        .id(item.getId())
        .street(item.getName())
        .city(item.getSinger())
        .county(item.getReleaseTime())
        .updatedDate(item.getUpdatedDate() != null ? item.getUpdatedDate()
            : Timestamp.valueOf(LocalDateTime.now()))
        .build();
  }

//  public static WishItem toWishItem(AddressEntity entity) {
//    return WishItem.builder()
//        .id(entity.getId())
//        .name(entity.getName())
//        .singer(entity.getSinger())
//        .releaseTime(entity.getReleaseTime())
//        .website(entity.getWebsite())
//        .userId(entity.getUserId())
//        .updatedDate(entity.getUpdatedDate())
//        .build();
//  }
}
