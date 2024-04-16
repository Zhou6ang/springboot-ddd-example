package com.example.hexagon.albummgt.album.driven.persistent;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.album.core.domain.DomainAlbumException;
import com.example.hexagon.albummgt.album.core.domain.ports.AlbumPersistent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultAlbumPersistentAdapter implements AlbumPersistent {

  private final AlbumRepository albumRepository;
  private final ArtistRepository artistRepository;
//    private final TransactionTemplate transactionTemplate;

  @Transactional
  @Override
  public Optional<AlbumAggregate> findById(String id) {
    Optional<AlbumEntity> opt = this.albumRepository.findById(Long.parseLong(id));
    if (!opt.isPresent()) {
      throw new DomainAlbumException("album not found");
    }
    AlbumEntity user = opt.get();
    ArtistEntity wishList = artistRepository.findByAlbumId(user.getId());
    AlbumAggregate userAgg = AlbumAggregate.builder().id(user.getId() + "")
        .title(user.getTitle())
        .price(user.getPrice())
        .userId(user.getUserId())
        .artist(ArtistEntity.toArtist(wishList))
        .updateDate(user.getUpdatedDate())
        .build();
    return Optional.of(userAgg);
  }

  @Transactional
  @Override
  public Long save(AlbumAggregate albumAggregate) {
    AlbumEntity album = albumRepository.save(AlbumEntity.toAlbumEntity(albumAggregate));
    if (albumAggregate.getArtist() != null) {
      Artist artist = albumAggregate.getArtist();
      artist.setAlbumId(album.getId());
      artistRepository.save(ArtistEntity.toArtistEntity(artist));
    }
    return album.getId();
  }

  @Override
  @Transactional
  public void delete(String id) {
    artistRepository.deleteByAlbumId(Long.parseLong(id));
    albumRepository.deleteById(Long.parseLong(id));
  }

  @Override
  @Transactional
  public Long update(AlbumAggregate albumAggregate) {
    AlbumEntity album = albumRepository.save(AlbumEntity.toAlbumEntity(albumAggregate));
    if (albumAggregate.getArtist() != null) {
      artistRepository.save(ArtistEntity.toArtistEntity(albumAggregate.getArtist()));
    }
    return album.getId();
  }

  @Override
  public List<AlbumAggregate> findAll() {
    List<AlbumEntity> albumList = albumRepository.findAll();
    Map<Long, List<AlbumAggregate>> mapper = albumList.stream().map(AlbumEntity::toAlbumAggregate)
        .collect(Collectors.groupingBy(e -> Long.parseLong(e.getId())));
    List<ArtistEntity> artistList = artistRepository.findAllByAlbumIdIn(
        new ArrayList<>(mapper.keySet()));
    if (!CollectionUtils.isEmpty(artistList)) {
      Map<String, List<ArtistEntity>> albumListMapper = artistList.stream()
          .collect(Collectors.groupingBy(e -> e.getAlbumId() + ""));
      return mapper.values().stream()
          .flatMap(e -> e.stream())
          .peek(e -> e.setArtist(
              albumListMapper.get(e.getId()).stream().map(ArtistEntity::toArtist).findFirst()
                  .orElse(null)))
          .collect(Collectors.toList());
    } else {
      return mapper.values().stream().flatMap(e -> e.stream()).collect(Collectors.toList());
    }
  }
}
