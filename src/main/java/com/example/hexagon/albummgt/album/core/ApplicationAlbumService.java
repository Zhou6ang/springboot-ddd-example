package com.example.hexagon.albummgt.album.core;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.DomainAlbumException;
import com.example.hexagon.albummgt.album.core.domain.service.DomainAlbumService;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class ApplicationAlbumService {

  private final DomainAlbumService domainAlbumService;

  public Long createAlbum(AlbumDTO req) {
    log.info("create user in application service");
    return domainAlbumService.createAlbum(req);
  }

  public void updateAlbum(AlbumDTO req) {
    log.info("update user in application service");
    if (StringUtils.isEmpty(req.getId())) {
      log.error("user id is empty");
      throw new DomainAlbumException("user id is empty");
    }
    domainAlbumService.updateAlbum(req);
  }

  public AlbumDTO getAlbum(String userId) {
    log.info("get user in application service");
    if (StringUtils.isEmpty(userId)) {
      log.error("user id is empty");
      throw new DomainAlbumException("user id is empty");
    }
    AlbumAggregate user = domainAlbumService.getAlbumById(userId);
    return AlbumDTO.builder().id(user.getId())
        .title(user.getTitle())
        .price(user.getPrice())
        .userId(user.getUserId())
        .artist(user.getArtist()).build();
  }

  public List<AlbumDTO> getAllAlbums() {
    log.info("get all user in application service");
    List<AlbumAggregate> list = domainAlbumService.findAllAlbum();
    return list.stream().map(user -> AlbumDTO.builder()
            .id(user.getId())
            .title(user.getTitle())
            .price(user.getPrice())
            .userId(user.getUserId())
            .artist(user.getArtist())
            .build())
        .collect(Collectors.toList());
  }

  public void deleteAlbum(String userId) {
    log.info("delete user in application service");
    if (StringUtils.isEmpty(userId)) {
      log.error("user id is empty");
      throw new DomainAlbumException("user id is empty");
    }
    domainAlbumService.deleteAlbumById(userId);
  }

  public void addWishlist(AlbumDTO req) {
    log.info("add wishlist in application service");
    if (StringUtils.isEmpty(req.getId())) {
      log.error("user id is empty");
      throw new DomainAlbumException("user id is empty");
    }

    if (req.getArtist() == null) {
      log.error("wishlist is empty");
      throw new DomainAlbumException("wishlist is empty");
    }
    domainAlbumService.addArtist(req.getId(), req.getArtist());
  }

}
