package com.example.shorturl.repository;

import com.example.shorturl.model.ShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByOriginalURL (String originalURL);
    ShortUrl findByShortURL (String shortURL);
}
