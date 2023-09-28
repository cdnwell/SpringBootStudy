package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationProxyTest {
    @Test
    void configuration() {
        // MyConfig를 구성정보에 등록하면 두 개 같다고 나옴
        // Configuration, ProxyBinMethods가 Default로 true로 설정되어 있는 경우
        // MyConfig가 Bean으로 등록될 때 직접 Bean으로 등록 되는 것이 아니라
        // Proxy Object를 앞에 하나 두고 Bean으로 등록된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class); // MyConfig 클래스 부트스트래핑
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    @Test
    void proxyCommonMethod() {
        // 이 프록시는 데코레이터처럼 타깃 오브젝트를 따로 두고 자기가 동적으로 끼어들어서
        // 중간에 어떤 중계하는 역할을 하는 게 아니라 애초에 이걸 확장해서 대체하는 방식으로 동작합니다.
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    // 어떤 식으로 Proxy가 만들어지는지 보여주겠다.
    static class MyConfigProxy extends MyConfig {
        Common common;

        @Override
        Common common() {
            if(this.common == null)
                common = super.common();

            return common;
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
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

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
