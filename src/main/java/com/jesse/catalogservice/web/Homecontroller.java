package com.jesse.catalogservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jesse.catalogservice.config.PolarProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Homecontroller {
    
    private final PolarProperties polarProperties;
    
    @GetMapping("/")
    public String getGreeting() {
        log.info("getGreeting() msg: {}", polarProperties.getGreetings());
        
        return polarProperties.getGreetings();
    }

}
