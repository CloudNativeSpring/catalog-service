package com.jesse.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.jesse.catalogservice.config.DataConfig;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTest {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;
    
    @Test
    public void findBookByIsbnWhenExisting() {
        String isbn = "1234567890";
        Book book = Book.builder()
                .isbn(isbn).title("Title").author("Author").price(12.90)
                .build();
        jdbcAggregateTemplate.insert(book); // 테스트에 필요한 데이터를 준비.
        
        Optional<Book> actualBook = bookRepository.findByIsbn(isbn);
        
        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().getIsbn()).isEqualTo(book.getIsbn());
    }
    
}
