package com.jesse.catalogservice.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.jesse.catalogservice.domain.Book;
import com.jesse.catalogservice.domain.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Profile("testdata")
public class BookDataLoader {

    private final BookRepository bookRepository;
    
    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        Book book1 = Book.builder()
                .isbn("1234567890")
                .title("Nothern Lights")
                .author("Lyra Silverstar")
                .price(9.90)
                .build();
        Book book2 = Book.builder()
                .isbn("1234567891")
                .title("Polar Journey")
                .author("Iorek Polarson")
                .price(12.90)
                .build();
        Book book3 = Book.builder()
                .isbn("1234567892")
                .title("Cloud Native Spring")
                .author("Thomas Vitale")
                .price(14.90)
                .build();
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }
    
}
