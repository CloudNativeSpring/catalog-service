package com.jesse.catalogservice.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.jesse.catalogservice.domain.Book;
import com.jesse.catalogservice.domain.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class InMemoryBookRepository implements BookRepository {
    
    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        log.info("findAll()");
        
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        log.info("findByIsbn(isbn={})", isbn);
        
        return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) : 
            Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        log.info("existsByIsbn(isbn={})", isbn);
        
        return books.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        log.info("save({})", book);
        
        books.put(book.getIsbn(), book);
        
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        log.info("deleteByIsbn(isbn={})", isbn);
        
        books.remove(isbn);
    }

}
