package com.example.shorturl.service;

import com.example.shorturl.model.ShortUrl;
import com.example.shorturl.model.ShortUrlDTO;
import com.example.shorturl.repository.URLRepository;
import com.example.shorturl.util.Base62;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@Slf4j
@Service
public class URLService {

    @Autowired
    private URLRepository urlRepository;
    @Autowired
    private RedisProvider redisProvider;

    /*
    post url ->
    get url from db
       -> exist
         return, update cache

       -> not exist
          save , update cache
     */

    private String generateShortURL(String longURL){
        String shortURL = Base62.toBase62(longURL);
        log.debug("{} -> {}",longURL,shortURL);
        return shortURL;
    }

    public Mono<String> postUrl(ShortUrl shortUrl){
        shortUrl.setShortURL(generateShortURL(shortUrl.getOriginalURL()));
       return getShortUrlByOriginalUrl(shortUrl)
                .filter(Objects::nonNull)
                .map(ShortUrl::getShortURL).log()
                .switchIfEmpty(
                        saveUrl(shortUrl).filter(Objects::nonNull).map(ShortUrl::getShortURL).log()
                        .doOnNext(shortUrl2 -> redisProvider.set(shortUrl))
                );
    }

    private Mono<ShortUrl> getShortUrlByOriginalUrl(ShortUrl shortUrl){
        return Mono.fromCallable(()->urlRepository.findByOriginalURL(shortUrl.getOriginalURL()))
                .subscribeOn(Schedulers.elastic())
                .log();
    }
    private Mono<ShortUrl> saveUrl(ShortUrl shortUrl){

        /*
        Spring 문서에서는 Jdbc 같은 blocking io는 별도의 쓰레에서 백그라운드로 돌려야한다고 한다.
        Mono.fromCallable 으로 blocking call 부분의 실행을 미루고
        Scheduler elastic을 사용하여 blocking 자원을 기다리는 별도의 쓰레드를 생성하여 실행한다.

        Scheduler elastic =
        An elastic thread pool. It creates new worker pools as needed, and reuse idle ones. This is a good choice for I/O blocking work for instance.

         */
        return Mono.fromCallable(()->urlRepository.save(shortUrl))
                .subscribeOn(Schedulers.elastic())
                .log();
    }

    public Mono<String> getUrlByShortUrl(String shortUrl){
        return Mono.fromCallable(()-> redisProvider.get(shortUrl))
                .filter(Objects::nonNull).log()
                .switchIfEmpty(
                        Mono.just(urlRepository.findByShortURL(shortUrl).getOriginalURL())
                ).doOnNext(oriURL -> redisProvider.set(
                        ShortUrl.builder()
                        .originalURL(oriURL)
                                .shortURL(shortUrl).build()))
                        .subscribeOn(Schedulers.elastic())
                .log();

    }


}
