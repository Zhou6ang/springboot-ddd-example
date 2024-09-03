package com.example.hexagon.albummgt.album.core;

import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.DomainAlbumException;
import com.example.hexagon.albummgt.album.core.domain.service.DomainAlbumService;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import com.example.hexagon.albummgt.album.driving.dto.AlbumRequest;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

  public AlbumDTO getAlbum(String userId, Long id) {
    log.info("get user in application service");
    if (StringUtils.isEmpty(id) || StringUtils.isEmpty(userId)) {
      log.error("userId or id is empty");
      throw new DomainAlbumException("userId or id is empty");
    }
    AlbumAggregate user = domainAlbumService.getAlbumByUserIdAndId(userId, id);
    return AlbumDTO.builder().id(user.getId())
        .title(user.getTitle())
        .price(user.getPrice())
        .userId(user.getUserId())
        .artist(user.getArtist()).build();
  }

  public Page<AlbumDTO> getAllAlbums(AlbumRequest req)  {
    log.info("get all user in application service");
    Page<AlbumAggregate> list = domainAlbumService.findAllAlbum(req);
    var result = list.stream().map(user -> AlbumDTO.builder()
            .id(user.getId())
            .title(user.getTitle())
            .price(user.getPrice())
            .userId(user.getUserId())
            .artist(user.getArtist())
            .build())
        .collect(Collectors.toList());
    return new PageImpl<>(result, list.getPageable(), list.getTotalElements());
  }

  public void deleteAlbum(String userId) {
    log.info("delete user in application service");
    if (StringUtils.isEmpty(userId)) {
      log.error("user id is empty");
      throw new DomainAlbumException("user id is empty");
    }
    domainAlbumService.deleteAlbumById(userId);
  }

}
