package com.jesse.catalogservice.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jesse.catalogservice.domain.BookNotFoundException;
import com.jesse.catalogservice.domain.BookService;

@WebMvcTest(BookController.class)
public class BookControllerMvcTest {

    @Autowired
    private MockMvc mockMvc; // (톰캣과 같은 서버를 로드하지 않고) 모의환경에서 웹 엔드포인트를 테스트할 수 있는 유틸리티.
    
    @MockBean // 슬라이스 외부의 빈이 필요한 경우 모의(Mock) 객체를 생성하고 주입.
    private BookService bookService;
    
    @Test
    public void whenGetBookNotExistsThenShouldReturn404() throws Exception {
        String isbn = "1212121212";
        
        // mock bean이 어떻게 작동할 것인 지 규정.
        given(bookService.viewBookDetails(isbn))
            .willThrow(BookNotFoundException.class);
        
        mockMvc
            .perform(get("/books/" + isbn))
            .andExpect(status().isNotFound());
    }
    
}
