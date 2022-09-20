package com.example.springboot.service;

import com.example.springboot.config.CacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlackListingService {
    @CachePut(value = CacheConfig.BLACKLIST_CACHE_NAME)
    public String blackListJwt(String jwt) {
        log.info("bbbbbbbbbb...............{}", jwt);
        return jwt;
    }

    @Cacheable(value = CacheConfig.BLACKLIST_CACHE_NAME, unless = "#result == null")
    public String getJwtBlackList(String jwt) {
        log.info("aaaaaaaa...............{}", jwt);
        return null;
    }
}
