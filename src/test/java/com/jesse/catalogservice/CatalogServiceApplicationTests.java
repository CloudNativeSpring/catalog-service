package com.jesse.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jesse.catalogservice.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired // setter-based dependency injection
    private WebTestClient webTestClient; // 테스트를 위해 REST 엔드포인트를 호출할 유틸리티.

    @Test
    public void whenGetRequestWithIdThenBookReturned() {
        String isbn = "1234567890123";
        Book bookToCreate = Book.builder()
                .isbn(isbn)
                .title("Title")
                .author("Author")
                .price(9.90)
                .publisher("Polarsophia")
                .build();
        webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull())
                .returnResult()
                .getResponseBody();
        
        webTestClient.get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book.getIsbn()).isEqualTo(bookToCreate.getIsbn());
                });
    }
    
    @Test
    public void whenPostRequestThenBookCreated() {
        Book expectedBook = Book.builder()
                .isbn("1234567890124")
                .title("Title")
                .author("Author")
                .price(9.99)
                .build();
        webTestClient.post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
//	                assertNotNull(actualBook);
                    assertThat(actualBook).isNotNull();
//	                assertEquals(expectedBook, actualBook);
                    assertThat(actualBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
                });
    }
    
    @Test
    public void whenPutRequestThenBookUpdated() {
        String isbn = "1234567890125";
        Book bookToCreate = Book.builder()
                .isbn(isbn)
                .title("Title")
                .author("Author")
                .price(9.90)
                .build();
        webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull())
                .returnResult()
                .getResponseBody();
        
        Book bookToUpdate = Book.builder()
                .isbn(bookToCreate.getIsbn())
                .title(bookToCreate.getTitle())
                .author(bookToCreate.getAuthor())
                .price(9.90 * 0.9)
                .publisher("Polarsophia")
                .build();
        webTestClient.put()
                .uri("/books/" + isbn)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book.getPrice()).isEqualTo(bookToUpdate.getPrice());
                    assertThat(book.getPublisher()).isEqualTo(bookToUpdate.getPublisher());
                });
    }
    
    @Test
    public void whenDeleteRequestThenBookDeleted() {
        String isbn = "1234567890126";
        Book bookToCreate = Book.builder()
                .isbn(isbn)
                .title("Title")
                .author("Author")
                .price(9.90)
                .publisher("Polarsophia")
                .build();
        webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus()
                .isCreated();
        
        webTestClient.delete()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus()
                .isNoContent();
        
        webTestClient.get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
