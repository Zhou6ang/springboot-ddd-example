package com.example.hexagon.albummgt.album.driving.dto;

import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.common.request.BaseRequest;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AlbumRequest extends BaseRequest {
  private Set<Long> ids;
  private String title;
  private BigDecimal priceFrom;
  private BigDecimal priceTo;
  private Long userId;
  private String artistName;
  private String artistBiography;
  private String artistBirthDay;
}
