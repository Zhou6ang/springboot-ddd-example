package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistent;
import com.example.hexagon.albummgt.user.core.exception.DomainUserException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
public class DefaultUserPersistentAdapter implements UserPersistent {

  private final UserRepository userRepository;
  private final WishItemRepository wishItemRepository;

  //    private final TransactionTemplate transactionTemplate;

  @Transactional
  @Override
  public Optional<UserAggregate> findById(String id) {
    return Optional.ofNullable(userRepository.findById(Long.parseLong(id))
        .map(UserEntity::toUserAggregate))
        .orElseThrow(()-> new DomainUserException("User not found"));
  }

  @Transactional
  @Override
  public Long save(UserAggregate userAggregate) {
    var userEntity = UserEntity.toUserEntity(userAggregate);
    userEntity.setWishItem(
        userAggregate.getWishlists().stream()
            .map(x -> WishItemEntity.fromUserAggregate(x, userEntity))
            .toList());
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
            .map(x -> WishItemEntity.fromUserAggregate(x, userEntity))
            .toList());
    UserEntity user = userRepository.save(userEntity);
    return user.getId();
  }

  @Override
  public List<UserAggregate> findAll() {
    List<UserEntity> userList = userRepository.findAll();
    Map<Long, List<UserAggregate>> mapper =
        userList.stream()
            .map(UserEntity::toUserAggregate)
            .collect(Collectors.groupingBy(e -> Long.parseLong(e.getId())));
    List<WishItemEntity> wishList =
        wishItemRepository.findAllByUserIdIn(new ArrayList<>(mapper.keySet()));
    if (!CollectionUtils.isEmpty(wishList)) {
      Map<String, List<WishItemEntity>> wishlistMapper =
          wishList.stream().collect(Collectors.groupingBy(e -> "")); // e.getUserId() +
      return mapper.values().stream()
          .flatMap(e -> e.stream())
          .peek(
              e -> {
                if (wishlistMapper.get(e.getId()) != null) {
                  e.addWishlists(
                      wishlistMapper.get(e.getId()).stream()
                          .map(WishItemEntity::toWishItem)
                          .collect(Collectors.toList()));
                }
              })
          .collect(Collectors.toList());
    } else {
      return mapper.values().stream().flatMap(e -> e.stream()).collect(Collectors.toList());
    }
  }
}
