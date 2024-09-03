package com.example.hexagon.albummgt.album.driving.rest;

import com.example.hexagon.albummgt.album.core.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import com.example.hexagon.albummgt.album.driving.dto.AlbumRequest;
import com.example.hexagon.albummgt.common.response.ResponseMsg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/{userId}/albums")
@Tag(name = "Album", description = "Album API")
public class AlbumController {

  private final ApplicationAlbumService applicationAlbumService;

  public AlbumController(ApplicationAlbumService applicationAlbumService) {
    this.applicationAlbumService = applicationAlbumService;
  }

  @Operation(summary = "Retrieve an album by its id for a user")
  @GetMapping("/{id}")
  public ResponseMsg getAlbumById(
      @PathVariable("userId") String userId, @PathVariable("id") Long id) {
    AlbumDTO result = applicationAlbumService.getAlbum(userId, id);
    return ResponseMsg.success("get album successfully", result);
  }

  @Operation(summary = "Retrieve all albums for a user")
  @GetMapping
  public ResponseMsg getAllAlbum(@PathVariable("userId") String userId) {
    return ResponseMsg.success(
        "get all albums successfully",
        applicationAlbumService.getAllAlbums(
            AlbumRequest.builder()
                .userId(Long.parseLong(userId))
                .pageNumber(1)
                .pageSize(1000)
                .build()));
  }

  @Operation(summary = "Filter all albums")
  @GetMapping("/search")
  public ResponseMsg search(
      @RequestParam(value = "id", required = false) Set<Long> ids,
      @RequestParam(value = "title", required = false) String title,
      @RequestParam(value = "priceFrom", required = false) BigDecimal priceFrom,
      @RequestParam(value = "priceTo", required = false) BigDecimal priceTo,
      @RequestParam(value = "userId", required = false) Long userId,
      @RequestParam(value = "artistName", required = false) String artistName,
      @RequestParam(value = "artistBiography", required = false) String artistBiography,
      @RequestParam(value = "artistBirthDay", required = false) String artistBirthDay,
      @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
      @RequestParam(value = "sortBy", required = false) String sortBy,
      @RequestParam(value = "sortDirection", required = false) String sortDirection) {

    var req =
        AlbumRequest.builder()
            .ids(ids)
            .title(title)
            .priceFrom(priceFrom)
            .priceTo(priceTo)
            .userId(userId)
            .artistName(artistName)
            .artistBiography(artistBiography)
            .artistBirthDay(artistBirthDay)
            .pageNumber(pageNum)
            .pageSize(pageSize)
            .sortBy(sortBy)
            .sortDirection(sortDirection)
            .build();
    return ResponseMsg.success(
        "filtering albums successfully", applicationAlbumService.getAllAlbums(req));
  }

  @Operation(summary = "Create an album for a user")
  @PostMapping
  public ResponseMsg createAlbum(
      @PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
    request.setUserId(userId);
    Long albumId = applicationAlbumService.createAlbum(request);
    return ResponseMsg.success("create album successfully, id: " + albumId);
  }

  @Operation(summary = "Update an album for a user")
  @PutMapping
  public ResponseMsg updateAlbum(
      @PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
    request.setUserId(userId);
    applicationAlbumService.updateAlbum(request);
    return ResponseMsg.success("update album success, id: " + request.getId());
  }

  @Operation(summary = "Delete an album by its id for a user")
  @DeleteMapping("/{id}")
  public ResponseMsg delete(
      @PathVariable("userId") String userId, @PathVariable("id") String albumId) {
    applicationAlbumService.deleteAlbum(albumId);
    return ResponseMsg.success("delete album success, id: " + userId);
  }
}
