package com.example.hexagon.albummgt.album.service;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Artist {
    private long id;
    private String name;
    private String biography;
    private String birthDay;
    private Long albumId;
    private Timestamp updatedDate;
}
