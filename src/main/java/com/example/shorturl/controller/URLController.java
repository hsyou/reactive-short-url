package com.example.shorturl.controller;

import com.example.shorturl.model.ShortUrl;
import com.example.shorturl.model.ShortUrlDTO;
import com.example.shorturl.repository.URLRepository;
import com.example.shorturl.service.URLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class URLController {

    @Autowired
    private URLService urlService;

    @GetMapping("/url")
    public Mono<ShortUrl> getLongURL(@RequestParam String code){
        return null;
    }

    @PostMapping("/url")
    public Mono<String> getShortURL(@RequestBody ShortUrl shortUrl){

        return urlService.postUrl(shortUrl)
                .log();
    }


    @GetMapping("/url/{code}")
    public Mono<String> test(@PathVariable String code){

        return urlService.getUrlByShortUrl(code);
    }
}
