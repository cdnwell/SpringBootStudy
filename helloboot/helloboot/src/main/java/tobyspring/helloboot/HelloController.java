package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {

    public String hello(String name) {
        SimpleHelloService helloService = new SimpleHelloService();
        // name이 넘어오지 않았다면 에러 발생시키기
        // error 던져도 됨.
        // 좀 더 나아가면 Objects.requireNonNull(name)
        // null이라면 예외를 던지고 null이 아니라면 값을 반환.
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
