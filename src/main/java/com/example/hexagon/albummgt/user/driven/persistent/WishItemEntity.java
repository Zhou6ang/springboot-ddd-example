package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.WishItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Data
@Entity(name = "wish_items")
@Builder
@AllArgsConstructor
public class WishItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String singer;

  @Column(name = "release_time")
  private String releaseTime;

  private String website;

  @Column(name = "created_date", insertable = false, updatable = false)
  private Timestamp createdDate;

  @Column(name = "updated_date", insertable = false, updatable = false)
  private Timestamp updatedDate;

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)})
  private UserEntity user;

  public WishItemEntity() {}

  public static WishItemEntity toEntity(WishItem item, UserEntity user) {
    return WishItemEntity.builder()
        .id(item.getId())
        .name(item.getName())
        .singer(item.getSinger())
        .releaseTime(item.getReleaseTime())
        .website(item.getWebsite())
        //        .userId(item.getUserId())
        .user(user)
        .build();
  }

  public static WishItem toDTO(WishItemEntity entity) {
    return WishItem.builder()
        .id(entity.getId())
        .name(entity.getName())
        .singer(entity.getSinger())
        .releaseTime(entity.getReleaseTime())
        .website(entity.getWebsite())
        .userId(entity.getUser().getId())
        .updatedDate(entity.getUpdatedDate())
        .build();
  }
}
