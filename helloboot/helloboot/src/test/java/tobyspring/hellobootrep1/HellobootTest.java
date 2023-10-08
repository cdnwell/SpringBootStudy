package tobyspring.hellobootrep1;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HellobootApplicationRep5.class)
@TestPropertySource("classpath:/application.properties")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Transactional
public @interface HellobootTest {
}
