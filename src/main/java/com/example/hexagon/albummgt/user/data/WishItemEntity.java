package com.example.hexagon.albummgt.user.data;

import com.example.hexagon.albummgt.user.service.WishItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity(name = "wish_items")
@Builder
@AllArgsConstructor
public class WishItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String singer;
    @Column(name = "release_time")
    private String releaseTime;
    private String website;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;

    public WishItemEntity() {
    }

    public static WishItemEntity fromUserAggregate(WishItem item) {
        return WishItemEntity.builder()
                .id(item.getId())
                .name(item.getName())
                .singer(item.getSinger())
                .releaseTime(item.getReleaseTime())
                .website(item.getWebsite())
                .userId(item.getUserId())
                .updatedDate(item.getUpdatedDate() != null ? item.getUpdatedDate() : Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    public static WishItem toWishItem(WishItemEntity entity) {
        return WishItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .singer(entity.getSinger())
                .releaseTime(entity.getReleaseTime())
                .website(entity.getWebsite())
                .userId(entity.getUserId())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }
}
