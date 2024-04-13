package com.example.hexagon.albummgt.album.driven.persistent;

import com.example.hexagon.albummgt.album.core.domain.Artist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@DynamicUpdate
@Data
@Entity(name = "artists")
@Builder
@AllArgsConstructor
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String biography;
    private String birthDay;
    @Column(name = "album_id")
    private long albumId;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;

    public ArtistEntity() {
    }

    public static ArtistEntity toArtistEntity(Artist item) {
        return ArtistEntity.builder()
                .id(item.getId())
                .name(item.getName())
                .biography(item.getBiography())
                .birthDay(item.getBirthDay())
                .albumId(item.getAlbumId())
                .updatedDate(item.getUpdatedDate() != null ? item.getUpdatedDate() : Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    public static Artist toArtist(ArtistEntity entity) {
        return Artist.builder()
                .id(entity.getId())
                .name(entity.getName())
                .biography(entity.getBiography())
                .birthDay(entity.getBirthDay())
                .albumId(entity.getAlbumId())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }
}
