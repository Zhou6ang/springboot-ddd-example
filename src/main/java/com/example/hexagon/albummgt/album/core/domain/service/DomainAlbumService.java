package com.example.hexagon.albummgt.album.core.domain.service;


import com.example.hexagon.albummgt.album.core.domain.AlbumAggregate;
import com.example.hexagon.albummgt.album.core.domain.Artist;
import com.example.hexagon.albummgt.album.core.domain.DomainAlbumException;
import com.example.hexagon.albummgt.album.core.domain.ports.AlbumPersistent;
import com.example.hexagon.albummgt.album.driving.dto.AlbumDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DomainAlbumService {

    private final AlbumPersistent albumPersistent;

    public Long createAlbum(AlbumDTO dto) {
        log.info("Create Album in domain service");
        AlbumAggregate aggregate = AlbumAggregate.builder().title(dto.getTitle()).price(dto.getPrice()).userId(dto.getUserId()).build();
        aggregate.setArtist(dto.getArtist());
        return albumPersistent.save(aggregate);
    }

    public void addArtist(String userId, Artist items) {
        log.info("add Artist in domain service");
        AlbumAggregate userAggregate = getAlbumById(userId);
        userAggregate.setArtist(items);
        albumPersistent.save(userAggregate);
    }

    public void removeArtist(String userId, Artist idList) {
        log.info("remove wishlist in domain service");
        AlbumAggregate userAggregate = getAlbumById(userId);
        userAggregate.setArtist(null);
        albumPersistent.save(userAggregate);
    }

    public Long updateAlbum(AlbumDTO req) {
        log.info("update user information in domain service");
        AlbumAggregate userAggregate = getAlbumById(req.getId());
        if(req.getTitle()!= null){
            userAggregate.setTitle(req.getTitle());
        }
        if(req.getPrice() != null){
            userAggregate.setPrice(req.getPrice());
        }
        // can't update userId due to rules
//        if(req.getUserId() != null){
//            userAggregate.setUserId(req.getUserId());
//        }
        if (req.getArtist() != null) {
            Artist reqArtist = req.getArtist();
            if(userAggregate.getArtist() == null){
                userAggregate.setArtist(reqArtist);
            }else{
                if(reqArtist.getName() != null){
                    userAggregate.getArtist().setName(reqArtist.getName());
                }
                if(reqArtist.getBiography() != null){
                    userAggregate.getArtist().setBiography(reqArtist.getBiography());
                }
                if(reqArtist.getBirthDay() != null){
                    userAggregate.getArtist().setBirthDay(reqArtist.getBirthDay());
                }
            }
        }
        return albumPersistent.update(userAggregate);
    }

    public AlbumAggregate getAlbumById(String userId) {
        log.info("get user information in domain service");
        return albumPersistent.findById(userId)
                .orElseThrow(() -> new DomainAlbumException("Album with given id doesn't exist"));
    }

    public void deleteAlbumById(String userId) {
        log.info("delete user in domain service");
        albumPersistent.delete(userId);
    }

    public List<AlbumAggregate> findAllAlbum() {
        log.info("find all user in domain service");
        return albumPersistent.findAll();
    }
}
