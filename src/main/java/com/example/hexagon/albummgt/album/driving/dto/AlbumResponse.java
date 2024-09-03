package com.example.hexagon.albummgt.album.driving.dto;

import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.common.request.BaseRequest;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import lombok.experimental.SuperBuilder;

public interface AlbumResponse {
  Long getId();
  String getTitle();
  String getArtist();
}
