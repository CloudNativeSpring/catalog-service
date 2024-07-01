package com.jesse.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookService bookService;
    
    @Test
    public void whenBookToCreateAlreadyExistsThenThrows() {
        String isbn = "1234567892";
        Book bookToCreate = Book.builder()
                .isbn(isbn).title("Title").author("Author").price(9.90)
                .build();
        when(bookRepository.existsByIsbn(isbn))
                .thenReturn(true);
        assertThatThrownBy(() -> bookService.addBookToCatalog(bookToCreate))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessage("A book with ISBN " + isbn + " already exists.");
    }
    
    @Test
    public void whenBookToReadDoesNotExistThenThrow() {
        String isbn = "1234567892";
        when(bookRepository.findByIsbn(isbn))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("The book with ISBN " + isbn + " was not found.");
    }

}
