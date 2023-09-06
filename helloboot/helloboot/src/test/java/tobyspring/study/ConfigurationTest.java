package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
//        Common common = new Common();
//        Assertions.assertThat(new Common()).isSameAs(new Common());
//        Assertions.assertThat(common).isSameAs(common);
        // -> true
//        MyConfig myConfig = new MyConfig();
//        Bean1 bean1 = myConfig.bean1();
//        Bean2 bean2 = myConfig.bean2();
//
//        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
        // -> false

        // MyConfig이라는 클래스를 Spring container의 구성정보를 사용하게 되면 동작 방식이 달라진다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
        // -> true, 두 주소 값이 동일하다.
        // @Configuration이 붙은 클래스가 Spring container에서 사용되어질 때 발생하는 마법 같은 것.
        // Spring의 설계에서 이것이 제일 불만이었다고 한다.
        // 자바 코드가 Spring 밖의 환경에서 사용되어질 때 어떻게 동작할지 기대하는 것이랑
        // Spring 안에서 등록되어져서 사용되어질 때 동작 방식이 달라지기 때문.
        // 기본적으로 Configuration ProxyBeanMethods 가 true일 경우
        //
    }

    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            // Bean1이라는 오브젝트를 만들 때는, 얘가 의존하고 있는 Common이라는 오브젝트를 주입해주어야 함.
            // 생성사를 이용해서 주입해야 하니
            // 위의 메소드로 만들어진 Common type의 오브젝트를 집어 넣어 주어야 함.
            // 가장 직관적인 방법.
            // common() 팩토리 메서드 넣기
            // return new Bean1(common());
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            // 이 떄, Bean1과 Bean2를 실행하면 common은 몇 번 실행될까요?
            // -> 2번
            // equals 연산자로 비교하면 같지 않다고 나와야 함.
            return new Bean2(common());
        }
    }

    // Bean1 <-- Common
    // Bean2 <-- Common

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }
}
