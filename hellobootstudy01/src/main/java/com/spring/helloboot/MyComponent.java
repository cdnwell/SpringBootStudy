package com.spring.helloboot;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 애노테이션을 생성하면 꼭 붙여주어야 하는 두 가지 메타 애노테이션이 있다.
// 1. Retention
// 이 애노테이션이 어디까지 살아 있을 것인가, 유지될 것인가에 대한 것.
// 일단은 고민 말고 RUNTIME
@Retention(RetentionPolicy.RUNTIME)
// 2. Target
// 애노테이션을 적용할 대상 지정 가능
// 애노테이션을 아무대나 붙이는게 아니라
// 지정된 타겟 위치에만 들어간다.
// 이것은 컴포넌트가 클래스 위에 붙은 것.
// 그래서 클래스나 인터페이스 같은 TYPE에 붙는다라고 정의함.
@Target(ElementType.TYPE)
// --- 여기까지가 일반적인 애노테이션 --- //
// 아래 추가로 메타 애노테이션으로 @Component 추가
@Component
public @interface MyComponent {
}
