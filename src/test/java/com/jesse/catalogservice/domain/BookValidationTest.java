package com.jesse.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class BookValidationTest {
    
    private static Validator validator;
    
    @BeforeAll // 테스트를 실행하기 전에 가장 먼저 실행할 코드 블록.
    // @BeforeAll 메서드는 반드시 static으로 선언해야.
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenAllFieldsCorrectThenValidationSucceeds() {
        Book book = Book.builder()
                .isbn("0123456789")
                .title("TITLE")
                .author("AUTHOR")
                .price(9.99)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }
    
    @Test
    public void whenIsbnDefinedButIncorrectThenValidationFails() {
        Book book = Book.builder()
                .isbn("a1234567890")
                .title("Title")
                .author("Author")
                .price(9.98)
                .build();
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
    
}
