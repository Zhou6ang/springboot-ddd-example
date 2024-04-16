package com.example.hexagon.albummgt.album.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class AlbumAggregate {

  private String id;
  private String title;
  private BigDecimal price;
  private String userId;
  private Timestamp updateDate;
  private Artist artist;
}
