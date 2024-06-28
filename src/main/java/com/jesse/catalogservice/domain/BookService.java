package com.jesse.catalogservice.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    
    public Iterable<Book> viewBookList() {
        log.info("viewBookList()");
        
        return bookRepository.findAll();
    }
    
    public Book viewBookDetails(String isbn) throws BookNotFoundException {
        log.info("viewBookDetails(isbn={})", isbn);
        
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }
    
    public Book addBookToCatalog(Book book) throws BookAlreadyExistsException {
        log.info("addBookToCatalog(book={})", book);
        
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BookAlreadyExistsException(book.getIsbn());
        }
        
        return bookRepository.save(book);
    }
    
    public void removeBookFromCatalog(String isbn) {
        log.info("removeBookFromCatalog(isbn={})", isbn);
        
        bookRepository.deleteByIsbn(isbn);
    }
    
    public Book editBookDetails(String isbn, Book book) {
        log.info("editBookDetails(isbn={}, book={})", isbn, book);
        
        return bookRepository.findByIsbn(isbn)
                .map(entity -> {
                    entity.updateBook(book);
                    return bookRepository.save(entity);
                })
                .orElseGet(() -> bookRepository.save(book));
    }
    
}
