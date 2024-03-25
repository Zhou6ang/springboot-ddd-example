package com.example.hexagon.albummgt.album.core.domain.ports;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;

import java.util.List;
import java.util.Optional;

public interface AlbumPersistentInterface {
    Optional<AlbumAggregate> findById(String id);

    Long save(AlbumAggregate userAggregate);

    void delete(String id);

    Long update(AlbumAggregate userAggregate);

    List<AlbumAggregate> findAll();
}
