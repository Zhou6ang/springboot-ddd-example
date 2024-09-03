package com.example.hexagon.albummgt.album.driven.persistent.repo;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.driven.persistent.entity.AlbumEntity;
import com.example.hexagon.albummgt.album.driven.persistent.entity.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomAlbumRepository {
  Page<AlbumAggregate> customFindAll(Specification<AlbumEntity> spec,Specification<ArtistEntity> joinSpec, Pageable page);
}
