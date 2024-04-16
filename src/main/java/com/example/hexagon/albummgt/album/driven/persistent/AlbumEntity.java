package com.example.hexagon.albummgt.album.driven.persistent;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity(name = "albums")
@Builder
@AllArgsConstructor
public class AlbumEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String title;
  private BigDecimal price;
  private String userId;
  @Column(name = "created_date")
  private Timestamp createdDate;
  @Column(name = "updated_date")
  private Timestamp updatedDate;

  public AlbumEntity() {//fix lombok issue that can't generate constructor with all arguments
  }

  public static AlbumEntity toAlbumEntity(AlbumAggregate user) {
    return AlbumEntity.builder()
        .id(user.getId() != null ? Long.parseLong(user.getId()) : 0)
        .title(user.getTitle())
        .price(user.getPrice())
        .userId(user.getUserId())
        .updatedDate(user.getUpdateDate() == null ? Timestamp.valueOf(LocalDateTime.now())
            : user.getUpdateDate())
        .build();
  }

  public static AlbumAggregate toAlbumAggregate(AlbumEntity user) {
    return AlbumAggregate.builder().id(user.getId() + "")
        .title(user.getTitle())
        .price(user.getPrice())
        .userId(user.getUserId())
        .updateDate(user.getUpdatedDate())
        .build();
  }
}
