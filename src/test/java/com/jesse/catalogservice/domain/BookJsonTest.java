package com.jesse.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonTest
public class BookJsonTest {
    
    private static Book book;
    
    @Autowired
    private JacksonTester<Book> jacksonTester; // JSON serialization/de-serialization을 테스트하기 위한 유틸리티.
    
    @BeforeAll
    public static void beforeTest() {
        book = Book.builder()
                .isbn("1234567890")
                .title("Cloud Native Spring")
                .author("Thomas Vitale")
                .price(9.99)
                .build();
        log.info(book.toString());
    }
    
    @Test
    public void testSerialize() throws Exception {
        JsonContent<Book> json = jacksonTester.write(book);
        
        assertThat(json).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.getIsbn());
        assertThat(json).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.getTitle());
        assertThat(json).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.getAuthor());
        assertThat(json).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.getPrice());
    }
    
    @Test
    public void testDeserialize() throws Exception {
        // since Java 15, multiline texts(text block feature)
        String content = """
                {
                    "isbn": "1234567890",
                    "title": "Cloud Native Spring",
                    "author": "Thomas Vitale",
                    "price": 9.99
                }
                """;
        System.out.println(content);
        
        ObjectContent<Book> object = jacksonTester.parse(content);
        
        assertThat(object)
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

}
