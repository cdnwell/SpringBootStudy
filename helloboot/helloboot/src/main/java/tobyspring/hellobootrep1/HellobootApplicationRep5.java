package tobyspring.hellobootrep1;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import tobyspring.config.MySpringBootApplication;

@MySpringBootApplication
public class HellobootApplicationRep5 {
    // 2개의 팩토리 메서드를 제거하면 현재 구조에서는 필요한 빈이 제공되지 않으므로 에러 발생
    // 어디선가는 빈 구성정보를 제공해주어야 함.
    // 빼고 싶은데, 이유는 Spring Initializr가 만든 Spring boot 앱과 거의 똑같은 걸 만들고 싶기 때문임.

//    @Bean
//    ApplicationRunner applicationRunner(Environment env) {
//        return args -> {
//            String name = env.getProperty("my.name");
//            System.out.println("my.name : " + name);
//        };
//    }
    private final JdbcTemplate jdbcTemplate;

    public HellobootApplicationRep5(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    public static void main(String[] args) {
        SpringApplication.run(HellobootApplicationRep5.class, args);
    }

}
