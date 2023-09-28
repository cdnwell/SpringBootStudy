package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class ConditionalTest {
    @Test
    void conditional() {
        // true
        // 어떤 Bean이 등록됐는가 등록되지 않았는가를 우리가 사용하는 테스트 Assert Library에서
        // 손쉽게 확인할 수 있또록 만들어진 테스트 전용 Application context가 있는데
        // 그걸 한 번 사용해보겠습니다.
        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        // 적용하고 싶은 Configuration = Config1
        // run이라는 메소드를 사용할 수 있는데 이 안에는 컨텍스트를 사용하는
        // Consumer 타입의 람다식을 넣을 수 있습니다.
        contextRunner.withUserConfiguration(Config1.class)
                // 이렇게 해서 이 config1을 애플리케이션 context에 빈으로 등록을 시켜 보고
                // 그렇게 해서 만들어진 context를 가지고 테스트를 해보는 거죠.
                .run(context -> {
                    // 이렇게 넘어온 컨텍스트에는 우리가 AssertJ같은 라이브러리에서 사용할 수 있는
                    // Bean이 존재하는 가를 체크하는 그런 특별한 Assert의 메소드들이 추가가 됩니다.
                    // 이제 이 configuration을 적용했을 때 이 타입의 빈이 등록된다 이걸 확인할 수 있죠
                    Assertions.assertThat(context).hasSingleBean(MyBean.class);
                    // Configuration이 붙은 클래스 자체도 빈으로 등록된다.
                    Assertions.assertThat(context).hasSingleBean(Config1.class);
                });

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(Config1.class);
        ac.refresh();

        MyBean bean = ac.getBean(MyBean.class);

        // false
//        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext();
//        ac2.register(Config2.class);
//        ac2.refresh();
//
//        MyBean bean2 = ac2.getBean(MyBean.class);

        // ApplicationContextRunner는 Spring boot에서 제공하는
        // 유틸리티 클래스 내용 테스트용 여기서는 AssertJ의 Assertion 메소드를
        // 추가적으로 제공해줍니다.
        new ApplicationContextRunner().withUserConfiguration(Config2.class)
                .run(context -> {
                    Assertions.assertThat(context).doesNotHaveBean(MyBean.class);
                    Assertions.assertThat(context).doesNotHaveBean(Config2.class);
                });

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueCondition.class)
    @interface TrueConditional {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional {
        // 이름을 value라고 하면 생략 가능
        // 싱글 엘리먼트만 있을 경우에 애너테이션을 사용할 수 있는 속성이 하나뿐일 때
        // 그 이름이 value 일 때 사용할 수 있다.
        boolean value();
    }

    // static class는 밖의 ConditionalTest와 상관없는 class가 된다.
    // 대신 얘는 밖에 있는 클래스가 패키지 역할을 해준다고 생각하면 된다.
    @Configuration
//    @TrueConditional
    @BooleanConditional(true)
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseCondition.class)
    @interface FalseConditional {}

    @Configuration
//    @FalseConditional
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    // 조건에 의해서 Bean이 등록되는지 확인하기 위한 static class
    static class MyBean {

    }

    static class TrueCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class FalseCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            Boolean value = (Boolean) annotationAttributes.get("value");
            return value;
        }
    }
}
