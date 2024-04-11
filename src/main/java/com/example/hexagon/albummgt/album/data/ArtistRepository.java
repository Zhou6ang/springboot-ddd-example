package com.example.hexagon.albummgt.album.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity,Long>{

    ArtistEntity findByAlbumId(Long albumId);

    @Modifying
    void deleteByAlbumId(Long albumId);
    List<ArtistEntity> findAllByAlbumIdIn(List<Long> albumIds);
}
