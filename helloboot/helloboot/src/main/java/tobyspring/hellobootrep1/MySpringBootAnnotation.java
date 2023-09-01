package tobyspring.hellobootrep1;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// 자바 애노테이션의 default 값 - 클래스, 컴파일된 클래스 파일 까지만 살아있지만 그 애노테이션이 달린 클래스를
// 런타임에 메모리로 로딩할 때는 그 정보가 사라짐.
@Target(ElementType.TYPE)
// TYPE = 3가지, class, interface, enum에 부여할 수 있는 애노테이션
@Configuration
@ComponentScan
public @interface MySpringBootAnnotation {
}
