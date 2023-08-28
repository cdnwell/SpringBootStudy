package tobyspring.hellobootrep1;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
// Component가 붙은 클래스를 찾아서 빈으로 등록해달라.
// application context가 등록된 hello boot application class에 ComponentScan이 붙으면
// 이 클래스가 있는 패키지 부터 시작해서 그 하위 패키지를 뒤져서 컴포넌트라는 애노테이션이 붙은 모든 클래스를
// 빈으로 등록함
// 빈으로 등록할 때 필요하다면, 의존 오브젝트를 찾아내고 생성자를 호출할 때 파라미터를 넘겨주기도 함.
// 굉장히 간편해 진듯.
// Scanner를 이용해서 스프링 컨테이너가 등록한 방식을 쓰면 좋은점이. 새로운 기능을 만들어서 추가할 때 매번
// 어디다가 구성정보를 등록해줄 필요 없이. 내가 작성하는 클래스가 빈으로 등록되어서 사용되어 질 것이라면,
// 간단하게, @Component 애노테이션만 붙여주면, 편리함.
// 하지만, 항상 좋은 것 만은 아니다.
// 모든 방식에는 장단점이 있는데,
// 이게 편리하기 때문에 많이 사용되어 지지만,
// 만약 빈으로 등록되는 클래스가 많아지게 되면,
// 이 애플리케이션을 실행했을 때 정확하게 어떤 것이 등록됐는가 찾아보려면, 굉장히 번거로울 수 있음
// 클래스를 다 뒤져서 Component 붙은 클래스들이 이런 것이 있으니.
// 이번에 애플리케이션이 실행하면 이런 클래스의 빈들이 만들어지겠구나, 이걸 체크 해봐야 함.
// 이렇게 ComponentScan에 단점 뿐만아니라 장점도 있긴 하지만,
// 우리가 작성하는 애플리케이션 로직을 담은 코드를 빈으로 등록하는 방식에서는 거의 이것이 표준
// 편리해서
// 어떤 클래스가 빈으로 등록되어 있는가는, 패키지 구성을 잘 하고 모듈을 잘 나눠서 개발을 하면 어렵지 않게 파악 가능
// 단점은 크게 부각 되지 않음,
// 그에 비해서 장점이 많기 때문에, 사용할 수 있다면 ComponentScan 방식을 적극 활용 추천

// 이전에는 에러, 이제는 Hellocontroller와 SimpleHelloService 빈이 잘 등록 되어서 동작을 했구나 확인 가능
//@ComponentScan
public class HellobootApplicationRep2 {

    public static void main(String[] args) {
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
        applicationContext.register(HellobootApplication.class);
        applicationContext.refresh();

    }
}
