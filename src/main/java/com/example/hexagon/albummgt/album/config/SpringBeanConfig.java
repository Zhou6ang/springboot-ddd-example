package com.example.hexagon.albummgt.album.config;

import com.example.hexagon.albummgt.album.service.ApplicationAlbumService;
import com.example.hexagon.albummgt.album.service.ports.AlbumPersistentService;
import com.example.hexagon.albummgt.album.service.DomainAlbumService;
import com.example.hexagon.albummgt.album.data.DefaultAlbumPersistentServiceAdapter;
import com.example.hexagon.albummgt.album.data.AlbumRepository;
import com.example.hexagon.albummgt.album.data.ArtistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("AlbumSpringConfig")
public class SpringBeanConfig {

    @Bean
    AlbumPersistentService albumPersistent(AlbumRepository albumRepository, ArtistRepository artistRepository) {
        return new DefaultAlbumPersistentServiceAdapter(albumRepository,artistRepository);
    }

    @Bean
    DomainAlbumService domainAlbumService(AlbumPersistentService userPersistent) {
        return new DomainAlbumService(userPersistent);
    }

    @Bean
    ApplicationAlbumService applicationAlbumService(DomainAlbumService domainAlbumService) {
        return new ApplicationAlbumService(domainAlbumService);
    }
}
