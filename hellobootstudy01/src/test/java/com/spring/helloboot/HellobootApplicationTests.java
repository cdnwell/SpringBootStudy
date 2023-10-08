package com.spring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HellobootApplicationTests {

	@Test
	void helloApi() {
		// http localhost:8080/hello?name=Spring 를 코드로 구현
		// HTTPie 프로그램(맥 os)
		RestTemplate restTemplate = new RestTemplate();
		// RestTemplate을 테스트로 쓸 때는 restTemplate은 에러 시 예외를 던짐. 불편함
		// 응답 자체를 가져와서 쓰고 싶으면 TestRestTemplate을 씀
		TestRestTemplate test = new TestRestTemplate();

		// {name} = 플레이스홀더, 치환자
		// message converting, binding
		// 마지막 Spring은 치환자에 들어갈 것
		// ResponseEntity는 웹 응답의 모든 요소를 갖고 있음.
		// type parameter는 body 부분이 String으로 되어있다는 뜻.
		ResponseEntity<String> res =
				restTemplate.getForEntity("http://127.0.0.1:9090/app/hello?name={name}", String.class, "Spring");

		// status code 200
		// 검증 jUnit assert 라이브러리 Spring initializer assertJ라는 라이브러리 있음.
		// assertJ 쓰겠음
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
		// header(content-type) text/plain
		// getFirst를 get 대신 쓰는 이유 = 같은 이름으로 여러 리스트를 가질 수 있다고 생각해 리스트로 반환
		// getFirst로 처음 하나만 반환
		// encoding 정보가 와서 테스트 실패
		// text/plain;charset=UTF-8
//		Assertions.assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.TEXT_PLAIN_VALUE);
		// startsWith로 테스트
		assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
		// body Hello Spring
		assertThat(res.getBody()).isEqualTo("*Hello Spring*");

		// 웹 응답의 3가지 요소 - status, header, body 검증 완료
	}

}
