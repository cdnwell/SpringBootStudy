package tobyspring.hellobootrep1;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
//public class HelloController implements ApplicationContextAware {
public class HelloController {
    private final HelloService helloService;
    // final로 만들면 안 됨. final 정의한 자바 필드는 생성자가 완료된 시점까지는 완성되어야 하는데,
    // 이 경우에는 생성자가 다 만들어지고 난 이후에 호출되는 메서드이기 때문에 final로 만들 수는 없음.
    // 이거를 컨테이너가 넣어준다는 얘기는, ApplicationContext
    // context 타입의 오브젝트도 container입장에서는 bean으로 등록해놓고 쓴다는 것
    // 조금 더 생각해보면, HelloController를 만들 때, 다른 필요한 빈이 있다면 생성자에 파라미터로 주입을
    // 해놓고 받게 할 수 있었는데, 여기서도 받을 수 있지 않을까.
    // ApplicationContextAware는 annotation이 자바가 등장하기도 전인 20년 전쯤 이미 나왔던 인터페이스
    // Spring 최신 방법 -> 생성자를 통해서 ApplicationContext를 주입하는 방법이 더 낫다.
    private ApplicationContext applicationContext;

//    public HelloController(HelloService helloService) {
//        this.helloService = helloService;
//    }
    // 생서자를 통해 ApplicationContext를 주입 받는 방법
    public HelloController(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }

    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println(applicationContext);
//        // 주입
//        this.applicationContext = applicationContext;
//    }
}
