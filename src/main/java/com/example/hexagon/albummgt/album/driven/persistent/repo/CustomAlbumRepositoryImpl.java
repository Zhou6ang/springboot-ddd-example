package com.example.hexagon.albummgt.album.driven.persistent.repo;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.album.driven.persistent.entity.AlbumEntity;
import com.example.hexagon.albummgt.album.driven.persistent.entity.ArtistEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class CustomAlbumRepositoryImpl implements CustomAlbumRepository {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public Page<AlbumAggregate> customFindAll(
      Specification<AlbumEntity> spec, Specification<ArtistEntity> joinSpec, Pageable page) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var query = criteriaBuilder.createQuery(AlbumAggregate.class);

    var albumRoot = query.from(AlbumEntity.class);
    var artistsRoot = query.from(ArtistEntity.class);

    var predicate = criteriaBuilder.and(
            criteriaBuilder.equal(artistsRoot.get("albumId"), albumRoot.get("id")),
            getPredicate(spec, albumRoot, query, criteriaBuilder),
            getPredicate(joinSpec, artistsRoot, query, criteriaBuilder)
        );

    var selection = criteriaBuilder.construct(
            AlbumAggregate.class,
            albumRoot.get("id"),
            albumRoot.get("title"),
            albumRoot.get("price"),
            albumRoot.get("userId"),
            albumRoot.get("updatedDate"),
            criteriaBuilder.construct(
                Artist.class,
                artistsRoot.get("id"),
                artistsRoot.get("name"),
                artistsRoot.get("biography"),
                artistsRoot.get("birthday"),
                artistsRoot.get("albumId"),
                artistsRoot.get("updatedDate")
            )
    );

    query
        .select(selection)
        .where(predicate)
        .orderBy(page.getSort().stream()
                .map(o ->o.isAscending()
                            ? criteriaBuilder.asc(albumRoot.get(o.getProperty()))
                            : criteriaBuilder.desc(albumRoot.get(o.getProperty())))
                .toList()
        );

    var result = entityManager
            .createQuery(query)
            .setFirstResult((int) page.getOffset())
            .setMaxResults(page.getPageSize())
            .getResultList();

    var total = getTotalCount(spec, joinSpec);
    return new PageImpl<>(result, page, total);
  }

  private Long getTotalCount(Specification spec, Specification joinSpec) {
    var criteriaBuilder = entityManager.getCriteriaBuilder();
    var countQuery = criteriaBuilder.createQuery(Long.class);

    var countRoot = countQuery.from(AlbumEntity.class);
    var artistsRoot = countQuery.from(ArtistEntity.class);

    var predicate =
        criteriaBuilder.and(
            criteriaBuilder.equal(artistsRoot.get("albumId"), countRoot.get("id")),
            getPredicate(spec, countRoot, countQuery, criteriaBuilder),
            getPredicate(joinSpec, artistsRoot, countQuery, criteriaBuilder));

    countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
    Long total = entityManager.createQuery(countQuery).getSingleResult();
    return total;
  }

  private <T> Predicate getPredicate(
      Specification<T> spec,
      Root<T> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    return Optional.ofNullable(spec.toPredicate(root, query, criteriaBuilder))
        .orElseGet(() -> criteriaBuilder.conjunction());
  }
}
