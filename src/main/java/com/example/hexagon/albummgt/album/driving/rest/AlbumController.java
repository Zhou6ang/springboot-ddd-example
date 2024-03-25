package com.example.hexagon.albummgt.album.driving.rest;

import com.example.hexagon.albummgt.album.core.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import com.example.hexagon.albummgt.common.response.ResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/{userId}/album")
public class AlbumController {

    private final ApplicationAlbumService applicationAlbumService;

    public AlbumController(ApplicationAlbumService applicationAlbumService) {
        this.applicationAlbumService = applicationAlbumService;
    }

    @GetMapping("/{id}")
    public ResponseMsg getAlbumById(@PathVariable("userId") String userId, @PathVariable("id") String id) {
        AlbumDTO result = applicationAlbumService.getAlbum(userId);
        return ResponseMsg.succ("get album successfully", result);
    }

    @GetMapping("")
    public ResponseMsg getAllAlbum(@PathVariable("userId") String userId) {
        return ResponseMsg.succ("get all albums successfully", applicationAlbumService.getAllAlbums());
    }

    @PostMapping("")
    public ResponseMsg createAlbum(@PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
        request.setUserId(userId);
        Long albumId = applicationAlbumService.createAlbum(request);
        return ResponseMsg.succ("create album successfully, id: "+ albumId);
    }

    @PutMapping("")
    public ResponseMsg updateAlbum(@PathVariable("userId") String userId, @RequestBody AlbumDTO request) {
        request.setUserId(userId);
        applicationAlbumService.updateAlbum(request);
        return ResponseMsg.succ("update album success, id: " + request.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseMsg delete(@PathVariable("userId") String userId, @PathVariable("id") String albumId) {
        applicationAlbumService.deleteAlbum(albumId);
        return ResponseMsg.succ("delete album success, id: " + userId);
    }
}