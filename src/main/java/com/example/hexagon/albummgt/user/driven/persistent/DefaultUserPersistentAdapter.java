package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistent;
import com.example.hexagon.albummgt.user.core.exception.DomainUserException;
import com.example.hexagon.albummgt.user.driving.dto.UserRequest;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class DefaultUserPersistentAdapter implements UserPersistent {

  private final UserRepository userRepository;
  private final WishItemRepository wishItemRepository;

  //    private final TransactionTemplate transactionTemplate;

  @Transactional
  @Override
  public Optional<UserAggregate> findById(String id) {
    return Optional.ofNullable(
            userRepository.findById(Long.parseLong(id)).map(UserEntity::toUserAggregate))
        .orElseThrow(() -> new DomainUserException("User not found"));
  }

  @Transactional
  @Override
  public Long save(UserAggregate userAggregate) {
    var userEntity = UserEntity.toUserEntity(userAggregate);
    userEntity.setWishItem(
        userAggregate.getWishlists().stream()
            .map(x -> WishItemEntity.toEntity(x, userEntity))
            .toList());
    userEntity.setAddress(AddressEntity.toAddressEntity(userAggregate.getAddress(), userEntity));
    UserEntity user = userRepository.save(userEntity);
    return user.getId();
  }

  @Override
  @Transactional
  public void delete(String id) {
    userRepository.deleteById(Long.parseLong(id));
  }

  @Override
  @Transactional
  public Long update(UserAggregate userAggregate) {
    var userEntity = UserEntity.toUserEntity(userAggregate);
    userEntity.setWishItem(
        userAggregate.getWishlists().stream()
            .map(x -> WishItemEntity.toEntity(x, userEntity))
            .toList());
    userEntity.setAddress(AddressEntity.toAddressEntity(userAggregate.getAddress(), userEntity));
    UserEntity user = userRepository.save(userEntity);
    return user.getId();
  }

  @Override
  public Page<UserAggregate> findAll(UserRequest request) {
    var pageRequest =
        PageRequest.of(
            request.getPageNumber() - 1,
            request.getPageSize(),
            Sort.by(
                Direction.fromOptionalString(request.getSortDirection()).orElse(Direction.DESC),
                request.getSortBy().split(",")));

      var spec =  Specification.<UserEntity>where(eq("name", request.getName()))
            .and(like("email", request.getEmail()))
            .and(in("id", request.getIds()))
            .and(leftJoinLike("wishItem", "name", request.getWishName()))
            .and(leftJoinLike("wishItem", "singer", request.getWishSinger()))
            .and(leftJoinLike("wishItem", "releaseTime", request.getWishReleaseTime()))
            .and(leftJoinEq("wishItem", "id", request.getWishUserId()))
            .and(leftJoinLike("address", "city", request.getCity()))
            .and(fetchAndLeftJoin("address"));

    Page<UserEntity> userList = userRepository.findAll(spec, pageRequest);
    return new PageImpl<>(
        userList.stream().map(UserEntity::toUserAggregate).collect(Collectors.toList()),
        userList.getPageable(),
        userList.getTotalElements());
  }

  static <T> Specification<T> eq(String field, String value) {
    return (root, query, criteriaBuilder) ->
        Optional.ofNullable(value)
            .filter(StringUtils::hasText)
            .map(x -> criteriaBuilder.equal(root.get(field), x))
            .orElseGet(criteriaBuilder::conjunction);
  }

  static <T> Specification<T> like(String field, String value) {
    return (root, query, criteriaBuilder) ->
        Optional.ofNullable(value)
            .filter(StringUtils::hasText)
            .map(x -> criteriaBuilder.like(root.get(field), "%" + x + "%"))
            .orElseGet(criteriaBuilder::conjunction);
  }

  static <T> Specification<T> in(String field, Set<?> value) {
    return (root, query, criteriaBuilder) ->
        Optional.ofNullable(value)
            .filter(DefaultUserPersistentAdapter::isNotEmpty)
            .map(x -> root.get(field).in(x))
            .orElseGet(criteriaBuilder::conjunction);
  }

  static <T> Specification<T> leftJoinLike(String leftJoinEntity, String field, String value) {
    return (root, query, criteriaBuilder) ->
        Optional.ofNullable(value)
            .filter(StringUtils::hasText)
            .map(
                x -> {
                  var leftJoin =
                      root.join(
                          leftJoinEntity,
                          JoinType.LEFT);
                  return criteriaBuilder.like(leftJoin.get(field), "%" + x + "%");
                })
            .orElseGet(criteriaBuilder::conjunction);
  }

  static <T> Specification<T> leftJoinEq(String leftJoinEntity, String field, Object value) {
    return (root, query, criteriaBuilder) ->
        Optional.ofNullable(value)
            .filter(
                x -> {
                  if (x instanceof String) {
                    return StringUtils.hasText((String) x);
                  } else if (x instanceof Integer || x instanceof Long || x instanceof Boolean) {
                    return x != null;
                  } else {
                    return false;
                  }
                })
            .map(
                x -> {
                  var leftJoin =
                      root.join(
                          leftJoinEntity,
                          JoinType.LEFT);
                  return criteriaBuilder.equal(leftJoin.get(field), x);
                })
            .orElseGet(criteriaBuilder::conjunction);
  }

  static <T> Specification<T> fetchAndLeftJoin(String ... leftJoinEntity) {
    return (root, query, criteriaBuilder) -> {
      if (query.getResultType() != Long.class && query.getResultType() != long.class) {
        for (String entity : leftJoinEntity) {
          root.fetch(entity, JoinType.LEFT); //TODO only for OneToOne to avoid N+1 problem.
        }
        query.distinct(true); // Ensure distinct results to avoid duplicates
      }
      return criteriaBuilder.conjunction();
    };
  }

  static <T> boolean isNotEmpty(Collection<T> list) {
    return !CollectionUtils.isEmpty(list);
  }
}
