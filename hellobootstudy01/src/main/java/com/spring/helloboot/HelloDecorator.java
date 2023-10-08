package com.spring.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Spring의 ComponentScanner가 스캔해서 사용할 수 있는 오브젝트가 되야 하는 데
// @Component도 되고
// @Service도 됨.
@Primary
@Service
public class HelloDecorator implements HelloService {
    private final HelloService helloService;

    // @Autowiring 이 안되는 것을 해결하는 방법
    // 1. 명시적으로 넣기
    // 2. Factory method를 이용해서 자바 코드에 의한 구성 정보를 만드는 방법을 써 봄.
    // 코드로 오브젝트의 의존관계의 순서를 지정 가능.
    // 후보가 2개인 것 중, 어느 것이 autowired로 선택될 것인가
    // -> @Primary(Bean이 두개 이상이면 우선 사용)

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        // 기능 추가 * *
        return "*" + helloService.sayHello(name) + "*";
    }

    @Override
    public int countOf(String name) {
        return helloService.countOf(name);
    }
}
