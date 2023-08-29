package tobyspring.hellobootrep1;

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
public class HellobootApplicationRep3 {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        // DispatcherServlet은 자기가 이용할 Controller를 찾아야 되기 때문에, Spring Container를
        // 넘겨 주어야 한다.
        // WebApplicationContext 생성자를 통해서 전달 함.
        // 그런데, Factory method에서 application context를 어떻게 전달?
        // onRefesh()라는 컨테이너를 초기화하는 도중에, 돌리는 이 메서드르 이용해서 생성할 때에는
        // 자기 자신을 파라미터로 넣어주었으면 되었는데
        // 메소드에서는 annotation context를 어떻게 전달할지.
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                // dispatcher servlet은 여기서 인스턴스를 생성하는 것이 아니라, dispatcher servlet도
                // Spring container로 부터 가져와야 함.
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // spring container 주입
                // this를 주입하는 방법
//                dispatcherServlet.setApplicationContext(this);
                // factory method를 통해서 두 개 오브젝트 등록

                // dispttcherServlet.setApplicationContext를 주석 처리 해도 정상 작동 함
                // Spring container가 application context가 필요함을 파악 하고.
                // 여기에 주입을 해 줌.
                // 이것을 이해하려면 Bean의 life cycle 메소드를 이해해야 함.

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });
                webServer.start();
            }
        };
        applicationContext.register(HellobootApplicationRep3.class);
        applicationContext.refresh();

    }
}
