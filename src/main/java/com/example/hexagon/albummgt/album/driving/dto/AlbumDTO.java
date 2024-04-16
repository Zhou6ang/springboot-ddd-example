package com.example.hexagon.albummgt.album.driving.dto;

import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.user.core.domain.WishItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class AlbumDTO {
  private String id;
  private String title;
  private BigDecimal price;
  private String userId;
  private Artist artist;
  @JsonProperty("update_date")
  private Timestamp updateDate;

}
