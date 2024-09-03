package com.example.hexagon.albummgt.album.driving.dto;

import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class AlbumDTO {
  private Long id;
  private String title;
  private BigDecimal price;
  private String userId;
  private Artist artist;

  @Hidden
  @JsonProperty("update_date")
  private Timestamp updateDate;
}
