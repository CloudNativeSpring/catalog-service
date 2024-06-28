package com.jesse.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jesse.catalogservice.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

    @Autowired // setter-based dependency injection
    private WebTestClient webTestClient; // 테스트를 위해 REST 엔드포인트를 호출할 유틸리티.

    @Test
    void whenPostRequestThenBookCreated() {
        Book expectedBook = Book.builder()
                .isbn("1234567890123")
                .title("Title")
                .author("Author")
                .price(9.99)
                .build();
        webTestClient
                .post()
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

}
