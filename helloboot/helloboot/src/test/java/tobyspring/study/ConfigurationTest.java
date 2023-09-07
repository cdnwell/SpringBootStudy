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

    // 프록시를 이용 하면, 위에서 @Configuration 가 붙은 클래스가 스프링 빈으로 사용 될 때
    // 보였던 독특한 동작 방식을 흉내낼 수 있음

    @Test
    void proxyCommonMethod() {
        // 이 proxy는 데코레이터 처럼 target object를 따로 두고 자기가 동적으로
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        // Spring container의 도움을 받은 것은 아니지만 직접 Proxy를 만들어서
        // Spring container 내부에
        // 이렇게 하면 우리가 확장한 이 proxy 오브젝트를 통해서 이 common method가 생성하는 오브젝트의
        // 개수를 하나로 제한하고 재사용할 수 있게 캐싱하는 방식을 동작하게 할 수 있다.
        // 팩토리 메서드를 사용해서 오브젝트를 생성하는 코드를 사용한다 할 지라도 딱 한 개의 오브젝트만
        // 사용되게 된다.
        // 이걸 자바 코드로도 가능하게 하다 보니 다르게 동작할 위험성이 생긴다.
        // 두 개 이상의 다른 빈에서 의존하고 있다면
        // 팩토리 메소드를 호출 할 때 마다 새로운 빈이 만들어짐
        // Spring은 그걸 해결하기 위해서 @Configuration이 붙은 클래스는 기본 적으로
        // 프록시를 만들어서 기능을 확장 해 줌.
        // 하지만, 작성해본 코드와 다른 방식으로 동작하기 때문에,
        // 이 부분에 대한 이해를 정확히 하고 있어야 함.
        // 3.0에서 도입된 방식 spring 5.2에서 Configuration의 element로 proxy bean method라는 엘리먼트를
        // 추가하고 proxy를 만드는 방식을 아예 끌 수 있게 해 줌.
        // 이걸 끈다라는 얘기는 @Configuration이 붙은 클래스가 proxy를 만들지 않고 자바코드 그대로
        // 동작하게 만듦.
        // @Configuration(proxyBeanMethods = false)로 바꿈
        // 그럼 첫 번 째 테스트가 실패함.
        // 그럼 @Configuration이 안붙은 @Component로 바꿔도 상관 없음
        // @Bean 애노테이션이 붙은 메소드들이 팩토리 메서드 처럼
        // @Bean 오브젝트를 생성하는 방식으로 Spring container가 사용하게 됨
        // 하지만 proxy를 만들어주지 않기 때문에, 우리가 기대했던 것과 다르게 동작할 수 있음
        // intellij는 proxyBeanMethods가 false인 경우 이렇게 메소드를 직접 호출 해서
        // bean의 의존 정보를 가져오는 것을 작성을 하면
        // 경고를 띄움
        // Spring 5.2부터 @Configuration의 속성을 proxyBeanMethods = false로 해서 적용하는 경우가
        // 많이 늘어남.
        // Bean 메서드를 통해서 오브젝트를 만들 때
        // 또 다른 Bean 메서드를 호출해서 의존 오브젝트를 가져오는 식으로 코드를 작성하지 않았다면
        // 굳이 매번 시간이 걸리고 비용이 드는 proxy를 만드는 방식으로 사용할 필요가 없음
        // Spring boot도 아까 보신 @MyAutoConfiguration 에 해당하는 Spring boot 의 AutoConfiguration
        // 해당하는 Spring boot의 AutoConfiguration도 bean의 팩토리 메서드는 다 false로 해놓고 사용 함.
        // Spring 공식 문서와 소스 코드에 달린 설명서를 읽어보면 조금씩 뉘앙스가 바뀌고 있음
        // 예전에는 특별한 이유가 없다면, proxyBeanMethods를 default인 true 로 사용 권장
        // false로 둘 경우 Bean 오브젝트를 직접 호출하는 방식으로 코드를 작성하면
        // 버그가 들어갈 수잇다 경고했는데, 최근에는 Bean 애노테이션이 붙은 메소드가 또 다른 빈 메소드를
        // 호출하는 방식으로 사용하지 않고, 이 자체로 이 빈을 생성하는 데 충분한 작업을 수행한다고 하면
        // 이것을 false로 두고 사용하는 것도 상관없다고 얘기함.
        // Spring에 적용 된 것 몇 가지
        // EnableSchedulling
        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    // MyConfig 클래스가 빈으로 등록 될 때 직접 빈으로 등록되는 것이 아니라
    // 프록시 오브젝트를 앞에 하나 두고 그게 빈으로 등록된다.
    // 프록시 패턴
    // 어떤 식으로 프록시가 만들어지는 지 간단하게 보여주겠다. 스프링 안에서 자동으로 일어남
    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if(this.common == null) this.common = super.common();

            // 일종의 캐싱
            return this.common;
        }
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
