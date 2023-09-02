package tobyspring.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

// @Component <- 스캐너에 의해서 클래스가 빈으로 등록되면서 이 안에 빈 애노테이션이 붙은
// 팩토리 메서드가 동작하겠죠.
// 이것 보다는 Configuration이 더 좋음
// 이것도 메타 애노테이션으로 @Component로 가지고 있음.
//@Configuration
public class Config {
}
