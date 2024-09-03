package com.example.hexagon.albummgt.album.core.domain.ports;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.driving.dto.AlbumRequest;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AlbumPersistent {

  Optional<AlbumAggregate> findById(Long id);

  Optional<AlbumAggregate> findByUserIdAndId(String userId, Long id);

  Long save(AlbumAggregate userAggregate);

  void delete(String id);

  Long update(AlbumAggregate userAggregate);

  Page<AlbumAggregate> findAll(AlbumRequest req);
}
