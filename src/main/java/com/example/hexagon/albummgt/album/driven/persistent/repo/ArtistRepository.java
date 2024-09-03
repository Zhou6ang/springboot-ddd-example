package com.example.hexagon.albummgt.album.driven.persistent.repo;

import com.example.hexagon.albummgt.album.driven.persistent.entity.ArtistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository
    extends JpaRepository<ArtistEntity, Long>, JpaSpecificationExecutor<ArtistEntity> {

  ArtistEntity findByAlbumId(Long albumId);

  @Modifying
  void deleteByAlbumId(Long albumId);

  List<ArtistEntity> findAllByAlbumIdIn(List<Long> albumIds);
}
