package tobyspring.hellobootrep1;

import org.springframework.boot.SpringApplication;
import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootApplicationRep5 {
    // 2개의 팩토리 메서드를 제거하면 현재 구조에서는 필요한 빈이 제공되지 않으므로 에러 발생
    // 어디선가는 빈 구성정보를 제공해주어야 함.
    // 빼고 싶은데, 이유는 Spring Initializr가 만든 Spring boot 앱과 거의 똑같은 걸 만들고 싶기 때문임.

    public static void main(String[] args) {
        SpringApplication.run(HellobootApplicationRep5.class, args);
    }

}
