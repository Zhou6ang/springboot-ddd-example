package com.example.hexagon.albummgt.album.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumAggregate {

  private Long id;
  private String title;
  private BigDecimal price;
  private String userId;
  private Timestamp updateDate;
  private Artist artist;
}
