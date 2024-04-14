package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.DomainUserException;
import com.example.hexagon.albummgt.user.core.domain.UserAggregate;
import com.example.hexagon.albummgt.user.core.domain.ports.UserPersistent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultUserPersistentAdapter implements UserPersistent {
    private final UserRepository userRepository;
    private final WishItemRepository wishItemRepository;
//    private final TransactionTemplate transactionTemplate;

    @Transactional
    @Override
    public Optional<UserAggregate> findById(String id) {
        Optional<UserEntity> opt = this.userRepository.findById(Long.parseLong(id));
        if (!opt.isPresent()) {
            throw new DomainUserException("User not found");
        }
        UserEntity user = opt.get();
        List<WishItemEntity> wishList = wishItemRepository.findByUserId(user.getId());
        UserAggregate userAgg = UserAggregate.builder().id(user.getId() + "")
                .name(user.getName())
                .gender(user.getGender())
                .email(user.getEmail())
                .phone(user.getPhone())
                .updateDate(user.getUpdatedDate())
                .build();
        if(!CollectionUtils.isEmpty(wishList)){
            userAgg.addWishlists(wishList.stream().map(WishItemEntity::toWishItem).collect(Collectors.toList()));
        }
        return Optional.of(userAgg);
    }

    @Transactional
    @Override
    public Long save(UserAggregate userAggregate) {
        UserEntity user = userRepository.save(UserEntity.toUserEntity(userAggregate));
        if (!CollectionUtils.isEmpty(userAggregate.getWishlists())) {
            List<WishItemEntity> wishlist = userAggregate.getWishlists().stream().peek(e -> e.setUserId(user.getId())).map(WishItemEntity::fromUserAggregate).collect(Collectors.toList());
            wishItemRepository.saveAll(wishlist);
        }
        return user.getId();
    }

    @Override
    @Transactional
    public void delete(String id) {
        wishItemRepository.deleteByUserId(Long.parseLong(id));
        userRepository.deleteById(Long.parseLong(id));
    }

    @Override
    @Transactional
    public Long update(UserAggregate userAggregate) {
        UserEntity user = userRepository.save(UserEntity.toUserEntity(userAggregate));
        if (!CollectionUtils.isEmpty(userAggregate.getWishlists())) {
            List<WishItemEntity> wishlist = userAggregate.getWishlists().stream().peek(e -> e.setUserId(user.getId())).map(WishItemEntity::fromUserAggregate).collect(Collectors.toList());
            List<WishItemEntity> toBeAdded = wishlist.stream().filter(e -> e.getId() == null).collect(Collectors.toList());
            if (!toBeAdded.isEmpty()) {
                wishItemRepository.deleteByUserId(user.getId());
            }
            wishItemRepository.saveAll(wishlist);
        } else {
            wishItemRepository.deleteByUserId(user.getId());
        }
        return user.getId();
    }

    @Override
    public List<UserAggregate> findAll() {
        List<UserEntity> userList = userRepository.findAll();
        Map<Long, List<UserAggregate>> mapper = userList.stream().map(UserEntity::toUserAggregate).collect(Collectors.groupingBy(e -> Long.parseLong(e.getId())));
        List<WishItemEntity> wishList = wishItemRepository.findAllByUserIdIn(new ArrayList<>(mapper.keySet()));
        if (!CollectionUtils.isEmpty(wishList)) {
            Map<String, List<WishItemEntity>> wishlistMapper = wishList.stream().collect(Collectors.groupingBy(e -> e.getUserId() + ""));
            return mapper.values().stream().flatMap(e -> e.stream()).peek(e -> {
                        if (wishlistMapper.get(e.getId()) != null) {
                            e.addWishlists(wishlistMapper.get(e.getId()).stream().map(WishItemEntity::toWishItem).collect(Collectors.toList()));
                        }
                    }
            ).collect(Collectors.toList());
        } else {
            return mapper.values().stream().flatMap(e -> e.stream()).collect(Collectors.toList());
        }

    }
}
