package com.example.hexagon.albummgt.album.driven.configuration;

import com.example.hexagon.albummgt.album.core.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.core.domain.ports.AlbumPersistentInterface;
import com.example.hexagon.albummgt.album.core.domain.service.DomainAlbumService;
import com.example.hexagon.albummgt.album.driven.persistent.AlbumPersistentAdapter;
import com.example.hexagon.albummgt.album.driven.persistent.AlbumRepository;
import com.example.hexagon.albummgt.album.driven.persistent.ArtistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("AlbumSpringConfig")
public class SpringBeanConfig {

    @Bean
    AlbumPersistentInterface albumPersistent(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        return new AlbumPersistentAdapter(albumRepository,artistRepository);
    }

    @Bean
    DomainAlbumService domainAlbumService(AlbumPersistentInterface userPersistent) {
        return new DomainAlbumService(userPersistent);
    }

    @Bean
    ApplicationAlbumService applicationAlbumService(DomainAlbumService domainAlbumService) {
        return new ApplicationAlbumService(domainAlbumService);
    }
}
