package tobyspring.hellobootrep1;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

// Bean 애노테이션을 붙인다음, 한 작업 더 Spring container가 bean 오브젝트를 가진 팩토리 메서드를 가진
// 클래스임을 인지하도록 클래스 레벨에도 애노테이션 하나 더 붙여주어야 함
// @Configuration -> 구성 정보를 가지고 있는 클래스다를 클래스 레벨에 붙여줌, 클래스를 스프링
// 컨테이너가 보고 이 안에 @Bean 애노테이션이 붙은 팩토리 메소드가 있겠구나
// 그걸 이용해서 Bean 오브젝트를 만들 수 있겠네 인식
// Configuration과 Bean이라는 애노테이션을 붙인 자바 코드로 만든 구성정보를 사용 하려면 두 가지 작업이
// 더 필요함
@Configuration
public class HellobootApplication {
    // factory 메서드-1
    // Spring container가 호출함.
    // Bean 오브젝트 만들기 위해 쓰이는 구나 -> @Bean 붙이기
//    @Bean
//    public HelloController helloController(HelloService helloService) {
//        return new HelloController(helloService);
//    }

    // factory 메서드-2
    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        // application context 종류가 굉장히 많이 있음, dsipatcherServlet은 WebApplication type 설정해 주어야
        // 기존 GenericWebApplicationContext는 자바 코드로 만든 Configuration 정보를 읽을 수가 없음
        // 그래서 이것을 살짝 바꾸어 주어야 하는데
        // AnnotationConfigWebApplicationContext으로 변경해 주어야 함
        // 앞에 Annotation Config이라 붙은게 annotation이 붙은 자바 코드를 이용해서 구성정보를 읽어오는
        // application context
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");
                });
                webServer.start();
            }
        };
//        applicationContext.registerBean(HelloController.class);
//        applicationContext.registerBean(SimpleHelloService.class);
//        applicationContext.refresh();
        // 앞에서는 registerBean을 이용해서 어떤 클래스를 이용해서 Bean 오브젝트를 만들 것인가
        // 하지만 AnnotationConfigWebApplicationContext에서는 지원하지 않음
        // 이 두 개의 코드가 필요 없다.
        // 대신, 자바 코드의 구성 정보를 가진 클래스를 등록해 주어야 한다.
        // HellobootApplication.class를 보면 자바 코드로 된 구성정보가 있으니 여기서 출발해서
        // Bean 오브젝트를 만들어 줘
        applicationContext.register(HellobootApplication.class);
        // refresh() == start()
        applicationContext.refresh();

        // 자바 코드로 구성 정보 바꾸어 보니 오히려 더 코드 정보가 늘어난 듯,
        // 단순히 오브젝트 생성하는 방식으로 만드는 것은 굳이 java config 사용할 필요 없음
        // 그런데, 이것이 적용된다라는 것을 보여드리기 위해 작성해 봄
        // 여기서 중요한 것은 @Configruation 붙은 이 클래스가 AnnotaionConfig을 이용하는
        // application context에 처음 등록된다는 사실
        // @Configuration 애노테이션이 붙은 클래스는, 빈 팩토리 메서드를 가지는 것 이상으로
        // 전체 애플리케이션을 구성하는 데 필요한 중요한 정보들을 많이 넣을 수 있기 때문
        // 다음 시간에는, Bean 팩토리 메서드 대신에 좀 더 간결한 방식으로 이 두개의 빈을 등록하는
        // 방법을 알아 봄
    }

}
