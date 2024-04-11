package com.example.hexagon.albummgt.album.presentation.dto;

import com.example.hexagon.albummgt.album.service.Artist;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
