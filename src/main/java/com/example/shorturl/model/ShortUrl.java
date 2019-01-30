package com.example.shorturl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String shortURL;
    @NotNull
    private String originalURL;
    private boolean status;

    public ShortUrlDTO toDTO(){
        return ShortUrlDTO.builder()
                .shortUrl(this.shortURL)
                .originalUrl(this.originalURL)
                .build();
    }

}
