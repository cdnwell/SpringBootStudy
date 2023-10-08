package tobyspring.config.autoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;
import tobyspring.config.ConditionalMyOnClass;
import tobyspring.config.EnableMyConfigurationProperties;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
//@Conditional(TomcatWebServerConfig.TomcatCondition.class)
@ConditionalMyOnClass("org.apache.catalina.startup.Tomcat")
//@Import(ServerProperties.class)
@EnableMyConfigurationProperties(ServerProperties.class)
public class TomcatWebServerConfig {
//    @Value("${contextPath:}")
//    String contextPath;
//
//    @Value("${port:8080}")
//    int port;

    @Bean("tomcatWebServerFactory")
    // 유저 구성 정보, 자동 구성 정보 중 얘보다 먼저 실행된 구성 정보 중 같은 것이 있다면 걔를 등록
    @ConditionalOnMissingBean
    public ServletWebServerFactory servletWebServerFactory(ServerProperties properties) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.setContextPath(env.getProperty("contextPath"));
        System.out.println(properties.getContextPath());

        factory.setContextPath(properties.getContextPath());
        factory.setPort(properties.getPort());

        return factory;
    }

//    @Bean
//    public ServerProperties serverProperties(Environment environment) {
//        ServerProperties properties = new ServerProperties();
//
//        properties.setContextPath(environment.getProperty("contextPath"));
//        properties.setPort(Integer.parseInt(environment.getProperty("port")));
//
//        return properties;
//    }

//    static class TomcatCondition implements Condition {
//        // 자동 구성 후보들을 다 불러와서 Spring container에 판단하게 함.
////        @Override
////        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
////            return false;
////        }
//
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat", context.getClassLoader()) ;
//        }
//    }
}
