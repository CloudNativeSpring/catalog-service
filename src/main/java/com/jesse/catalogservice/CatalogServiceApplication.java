package com.jesse.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan // 스프링 컨텍스트에서 설정 데이터 빈이 있는 지 스캔하도록 지시. 설정 데이터 빈을 로드.
// n.b. @EnableConfigurationProperties: 직접 설정 클래스를 지정.
public class CatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}
