package com.example.helloboot.controller;

import com.example.helloboot.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/hello")
@Controller
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(String name) {
        // 만일 null이라면 예외를 던지고 그렇지 않다면 원래 값을 전달한다.
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
