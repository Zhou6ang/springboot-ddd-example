package com.example.hexagon.albummgt.user.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class WishItem {

  @Hidden
  private long id;
  @Schema(example = "The Forgotten Night", minLength = 1, maxLength = 50)
  private String name;
  @Schema(example = "lady gaga", minLength = 1, maxLength = 50)
  private String singer;
  @Schema(example = "2021", minLength = 1, maxLength = 50)
  @JsonProperty("release_time")
  private String releaseTime;
  @Schema(example = "www.yoyo.com", minLength = 1, maxLength = 50)
  private String website;
  @Hidden
  private Long userId;
  @Hidden
  private Timestamp updatedDate;
}
