package com.example.hexagon.albummgt.album.core.domain;

import io.swagger.v3.oas.annotations.Hidden;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

  private Long id;
  private String name;
  private String biography;
  private String birthDay;
  private Long albumId;
  @Hidden private Timestamp updatedDate;
}
