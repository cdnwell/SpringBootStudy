package tobyspring.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
//@Conditional(JettyWebServerConfig.JettyCondition.class)
@ConditionalMyOnClass("org.eclipse.jetty.server.Server")
public class JettyWebServerConfig {
    @Bean("jettyWebServerFactory")
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }

//    static class JettyCondition implements Condition {
//        // JettyWebServerConfig이라는 클래스를 빈으로 등록할 건지 boolean 값으로 결정
//        // metadata : Conditional이 붙어 있는 이거를 메타 애노테이션으로 사용하고 있는 그 위치에 붙은
//        // 다른 애노테이션의 정보들을 이용할 수 있도록 그 애노테이션과 관련된 메타 데이터를 리턴해줌
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            // Jetty Configuration에 붙은 Conditional에서는 이 Condition이 무조건 true를
//            // return하게 할 겁니다.
////            return true;
//            return ClassUtils.isPresent("org.eclipse.jetty.server.Server", context.getClassLoader());
//        }
//    }
}
