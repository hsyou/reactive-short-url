package com.example.shorturl.service;

import com.example.shorturl.model.ShortUrl;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RedisProvider {

    @Autowired
    private StatefulRedisConnection<String, String> redisConnection;

    public void set(ShortUrl shortUrl){
        redisConnection.sync().setex(shortUrl.getShortURL(), 100, shortUrl.getOriginalURL());
    }

    public String get(String shortUrl){
        return redisConnection.sync().get(shortUrl);
    }
}
