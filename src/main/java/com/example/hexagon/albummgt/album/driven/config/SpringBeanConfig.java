package com.example.hexagon.albummgt.album.driven.config;

import com.example.hexagon.albummgt.album.core.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.core.domain.ports.AlbumPersistent;
import com.example.hexagon.albummgt.album.core.domain.service.DomainAlbumService;
import com.example.hexagon.albummgt.album.driven.persistent.DefaultAlbumPersistentAdapter;
import com.example.hexagon.albummgt.album.driven.persistent.AlbumRepository;
import com.example.hexagon.albummgt.album.driven.persistent.ArtistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("AlbumSpringConfig")
public class SpringBeanConfig {

    @Bean
    AlbumPersistent albumPersistent(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        return new DefaultAlbumPersistentAdapter(albumRepository,artistRepository);
    }

    @Bean
    DomainAlbumService domainAlbumService(AlbumPersistent userPersistent) {
        return new DomainAlbumService(userPersistent);
    }

    @Bean
    ApplicationAlbumService applicationAlbumService(DomainAlbumService domainAlbumService) {
        return new ApplicationAlbumService(domainAlbumService);
    }
}
