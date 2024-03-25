package com.example.hexagon.albummgt.user.core.domain;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class WishItem {
    private long id;
    private String name;
    private String singer;
    private String releaseTime;
    private String website;
    private Long userId;
    private Timestamp updatedDate;
}
