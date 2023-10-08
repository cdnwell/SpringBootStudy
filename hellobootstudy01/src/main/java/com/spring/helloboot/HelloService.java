package com.spring.helloboot;

import org.springframework.stereotype.Component;

public interface HelloService {
    String sayHello(String name);
    default int countOf(String count) {
        return 0;
    }
}
