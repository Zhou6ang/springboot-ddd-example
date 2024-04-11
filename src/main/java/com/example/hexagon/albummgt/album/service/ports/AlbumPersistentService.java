package com.example.hexagon.albummgt.album.service.ports;

import com.example.hexagon.albummgt.album.service.AlbumAggregate;

import java.util.List;
import java.util.Optional;

public interface AlbumPersistentService {
    Optional<AlbumAggregate> findById(String id);

    Long save(AlbumAggregate userAggregate);

    void delete(String id);

    Long update(AlbumAggregate userAggregate);

    List<AlbumAggregate> findAll();
}
