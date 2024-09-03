package com.example.hexagon.albummgt.album.driven.persistent.repo;

import com.example.hexagon.albummgt.album.driven.persistent.entity.AlbumEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository
    extends JpaRepository<AlbumEntity, Long>, JpaSpecificationExecutor<AlbumEntity>, CustomAlbumRepository {
  Optional<AlbumEntity> findByUserIdAndId(String userId, Long id);
}
