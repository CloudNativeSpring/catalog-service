package com.jesse.catalogservice.demo;

import java.util.List;

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
        bookRepository.deleteAll(); // 빈 데이터베이스로 시작하기 위해 기존 책이 있다면 모두 삭제한다.
        
        Book book1 = Book.builder()
                .isbn("1234567891")
                .title("Nothern Lights")
                .author("Lyra Silverstar")
                .price(9.90)
                .build();
        Book book2 = Book.builder()
                .isbn("1234567892")
                .title("Polar Journey")
                .author("Iorek Polarson")
                .price(12.90)
                .build();
        
        bookRepository.saveAll(List.of(book1, book2));
    }
    
}
