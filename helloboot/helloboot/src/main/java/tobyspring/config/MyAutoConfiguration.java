package tobyspring.config;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configuration(proxyBeanMethods = false)
// MyAutoConfiguration 애노테이션을 클래스에 붙이면 @Configuration 붙인 것과 동일한 효과
public @interface MyAutoConfiguration {
}
