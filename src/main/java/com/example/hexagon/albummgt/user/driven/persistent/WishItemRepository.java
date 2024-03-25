package com.example.hexagon.albummgt.user.driven.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishItemRepository extends JpaRepository<WishItemEntity,Long>{

    List<WishItemEntity> findByUserId(Long userId);

    @Modifying
    void deleteByUserId(Long userId);
    List<WishItemEntity> findAllByUserIdIn(List<Long> userIds);
}
