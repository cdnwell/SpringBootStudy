package tobyspring.hellobootrep1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
//@ComponentScan
public class HellobootApplicationRep4 {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    // 이것은 많이 보던 spring boot application 루트 파일과 같다.
    // spring initializer가 만들어준 기본 코드와 동일하다.
    // 우리가 지금까지 해온 작업이 Spring boot가 stand-alone으로 servlet container까지 포함하는
    // spring application을 동작시키는 그 원리가 담겨진 코드임.
    // 물론, spring boot app과 다르게 factory method 두 개가 있고.
    // spring boot app은 annotation 하나만 있었음
    // 강의 후반 부에서는 spring boot와 같이 만들어 보겠음.
    public static void main(String[] args) {
        // MySpringApplication 클래스는 이제 그만 사용하고 Spring boot가 만들어준 이것보다 훨씬
        // 뛰어나지만 동일한 동작을 하는 것으로 바꿔보겠음.
//        MySpringApplication.run(HellobootApplicationRep4.class, args);
        SpringApplication.run(HellobootApplicationRep4.class, args);

        // factory method와 Configuration 애노테이션이 필요 없겟지 -> 그렇지 않음.
        // 필요하다.
        // ServletWebServerFactory 메서드를 삭제하면 필요하다는 메시지가 나옴.
        // 달라진 이유는
    }

    // main 메서드 밖으로 빠져나옴
    // method 재활용 하려면 appliationClass라는 이름의 파라미터로 받아서 사용하도록 메서드 만듦.
    // applicationClass에 들어가야 할 중요한 사항은
    // 1. @Configuration이 붙은 클래스여야 하고
    // 2. @ComponentScan과 factory method를 가지고 spring container에게 애플리케이션 구성을
    // 어떻게 할 것인가를 알려주는 정보를 갖고 있는 클래스 여야 함.
    // command 라인에서도 실행 가능 한 메인 메서드 이니깐 args 도 넘겨보겠음.

}
