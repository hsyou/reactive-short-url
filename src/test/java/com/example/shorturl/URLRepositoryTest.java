package com.example.shorturl;

import com.example.shorturl.model.ShortUrl;
import com.example.shorturl.repository.URLRepository;
import com.example.shorturl.service.URLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class URLRepositoryTest {

    @Autowired
    private URLService urlService;


    private final String shortURL = "R3vSz4";
    private final String originalURL = "https://www.google.com";

    @Before
    public void init() {
        ShortUrl shortUrl = ShortUrl.builder()
                .shortURL(shortURL)
                .originalURL(originalURL)
                .build();

    }
    @Test
    public void reactive_shorturl로_가져오기(){

        ShortUrl shortUrl = ShortUrl.builder()
                .shortURL(shortURL)
                .originalURL(originalURL)
                .build();

        Mono<ShortUrl> shortUrlMono = urlService.saveUrl(shortUrl);
        shortUrlMono.subscribe(

        );

//        assertThat(urlRepositry.findByShortURL(shortURL).getOriginalURL().equals(originalURL));
    }
}
