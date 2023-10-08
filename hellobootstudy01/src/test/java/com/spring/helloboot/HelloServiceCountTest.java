package com.spring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class HelloServiceCountTest {
    @Autowired
    HelloService helloService;

    @Autowired
    HelloRepository helloRepository;

    @Test
    void sayHelloIncreaseCount() {
        // 1부터 10까지 늘어나는 매번 람다식을 호출하는 것
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloService.sayHello("Toby");
            Assertions.assertThat(helloRepository.countOf("Toby")).isEqualTo(count);
        });
    }

}
