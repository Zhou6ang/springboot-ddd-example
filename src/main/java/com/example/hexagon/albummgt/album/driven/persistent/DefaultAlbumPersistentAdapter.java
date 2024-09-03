package com.example.hexagon.albummgt.album.driven.persistent;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.album.core.domain.DomainAlbumException;
import com.example.hexagon.albummgt.album.core.domain.ports.AlbumPersistent;
import com.example.hexagon.albummgt.album.driven.persistent.entity.AlbumEntity;
import com.example.hexagon.albummgt.album.driven.persistent.entity.ArtistEntity;
import com.example.hexagon.albummgt.album.driven.persistent.repo.AlbumRepository;
import com.example.hexagon.albummgt.album.driven.persistent.repo.ArtistRepository;
import com.example.hexagon.albummgt.album.driving.dto.AlbumRequest;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class DefaultAlbumPersistentAdapter implements AlbumPersistent {

  private final AlbumRepository albumRepository;
  private final ArtistRepository artistRepository;

  //    private final TransactionTemplate transactionTemplate;

  @Transactional
  @Override
  public Optional<AlbumAggregate> findById(Long id) {
    Optional<AlbumEntity> opt = this.albumRepository.findById(id);
    if (!opt.isPresent()) {
      throw new DomainAlbumException("album not found");
    }
    AlbumEntity user = opt.get();
    ArtistEntity wishList = artistRepository.findByAlbumId(user.getId());
    AlbumAggregate userAgg =
        AlbumAggregate.builder()
            .id(user.getId())
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
  public Optional<AlbumAggregate> findByUserIdAndId(String userId, Long id) {
    Optional<AlbumEntity> opt = albumRepository.findByUserIdAndId(userId, id);
    if (!opt.isPresent()) {
      throw new DomainAlbumException("album not found");
    }
    AlbumEntity user = opt.get();
    ArtistEntity wishList = artistRepository.findByAlbumId(user.getId());
    AlbumAggregate userAgg =
        AlbumAggregate.builder()
            .id(user.getId())
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
  public Page<AlbumAggregate> findAll(AlbumRequest req) {
    var pageRequest =
        PageRequest.of(
            req.getPageNumber() - 1,
            req.getPageSize(),
            Sort.by(
                Direction.fromOptionalString(req.getSortDirection()).orElse(Direction.DESC),
                StringUtils.hasText(req.getSortBy())
                    ? req.getSortBy().split(",")
                    : new String[] {"id"}));

    var spec =
        Specification.<AlbumEntity>where(in("id", req.getIds()))
            .and(like("title", req.getTitle()))
            .and(btw("price", req.getPriceFrom(), req.getPriceTo()))
            .and(eq("userId", req.getUserId()));

    var joinSpec =
        Specification.<ArtistEntity>where(like("name", req.getArtistName()))
            .and(like("biography", req.getArtistBiography()))
            .and(like("birthday", req.getArtistBirthDay()));

    return albumRepository.customFindAll(spec, joinSpec, pageRequest);
  }

  static <T> Specification<T> eq(String field, Object value) {
    return value != null
        ? (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value)
        : null;
  }

  static <T> Specification<T> like(String field, String value) {
    return StringUtils.hasText(value)
        ? (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(field), "%" + value + "%")
        : null;
  }

  static <T> Specification<T> in(String field, Set<?> value) {
    return CollectionUtils.isEmpty(value)
        ? null
        : (root, query, criteriaBuilder) -> root.get(field).in(value);
  }

  static <T> Specification<T> btw(String field, BigDecimal from, BigDecimal to) {
    return from != null && to != null
        ? (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(field), from, to)
        : null;
  }

  static <T> Specification<T> joinLike(String leftJoinEntity, String field, String value) {
    return Objects.nonNull(value)
        ? (root, query, criteriaBuilder) -> {
          var leftJoin = root.join(leftJoinEntity, JoinType.LEFT); //only available for defined relationship, i.e. @OneToMany
          return criteriaBuilder.like(leftJoin.get(field), "%" + value + "%");
        }
        : null;
  }
}
