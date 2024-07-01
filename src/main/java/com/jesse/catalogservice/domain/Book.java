package com.jesse.catalogservice.domain;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @ToString @EqualsAndHashCode
public class Book {
    
    @Id
    private Long id; //PK. technical key. surrogate key.
    
    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(
            regexp = "^[0-9]{10}|[0-9]{13}",
            message = "The ISBN format must be valid."
    )
    private String isbn; // natural key. business key.
    
    @NotBlank(message = "The book title must be defined.")
    private String title;
    
    @NotBlank(message = "The book author must be defined.")
    private String author;
    
    @NotNull(message = "The book price must be defined.")
    @Positive(message = "The book price must be greater than zero.")
    private Double price;
    
    @CreatedDate
    private Instant createdDate;
    
    @LastModifiedDate
    private Instant lastModifiedDate;
    
    @Version
    private int version; // optimistic locking(낙관적 잠금)을 위해 사용하는 엔터티 버전 번호
    
    public void updateBook(Book book) {
        this.title = book.title;
        this.author = book.author;
        this.price = book.price;
    }
}
