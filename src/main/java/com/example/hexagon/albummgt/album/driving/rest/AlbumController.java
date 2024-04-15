package com.example.hexagon.albummgt.album.driving.rest;

import com.example.hexagon.albummgt.album.core.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import com.example.hexagon.albummgt.common.response.ResponseMsg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResponseMsg getAlbumById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        AlbumDTO result = applicationAlbumService.getAlbum(userId);
        return ResponseMsg.succ("get album successfully", result);
    }

    @Operation(summary = "Retrieve all albums for a user")
    @GetMapping("")
    public ResponseMsg getAllAlbum(@PathVariable("userId") String userId) {
        return ResponseMsg.succ("get all albums successfully", applicationAlbumService.getAllAlbums());
    }

    @Operation(summary = "Create an album for a user")
    @PostMapping("")
    public ResponseMsg createAlbum(@PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
        request.setUserId(userId);
        Long albumId = applicationAlbumService.createAlbum(request);
        return ResponseMsg.succ("create album successfully, id: "+ albumId);
    }

    @Operation(summary = "Update an album for a user")
    @PutMapping("")
    public ResponseMsg updateAlbum(@PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
        request.setUserId(userId);
        applicationAlbumService.updateAlbum(request);
        return ResponseMsg.succ("update album success, id: " + request.getId());
    }

    @Operation(summary = "Delete an album by its id for a user")
    @DeleteMapping("/{id}")
    public ResponseMsg delete(@PathVariable("userId") String userId, @PathVariable("id") String albumId) {
        applicationAlbumService.deleteAlbum(albumId);
        return ResponseMsg.succ("delete album success, id: " + userId);
    }
}