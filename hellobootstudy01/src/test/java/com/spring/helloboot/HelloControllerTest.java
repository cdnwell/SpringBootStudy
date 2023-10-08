package com.spring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HelloControllerTest {

    @Test
    void helloController() {
        // hello controller에만 집중해서 테스트 하고 싶음.
        // hello service 에 인자를 주어야 하는 문제가 생김
        // spring container가 없으니 이 상태에서 생성자에 주입 해주려면 hello service가 호출되는 구간을
        // 아주 심플하게 만들어서 집어 넣을 수 있음.
        // 이런 것을 test stub이라고도 함.
        // 하는 방법은 간단함 익명 클래스 만들어서 구현
        // 인터페이스(HelloServce)가 메서드가 하나라 람다로 함.
        HelloController helloController = new HelloController(name -> name);

        String ret = helloController.hello("Test");

        // 이것 역시 수행속도가 굉장히 빠름
        Assertions.assertThat(ret).isEqualTo("Test");

        // 이 테스트를 만든 이유가 있음.
        // 충분하게 하려면 파라미터도 여러가지 넣고 해야 함.
        // 비정상적인 상황도 가정 해야 함.
        // 웹 컴포넌트가 완벽하게 동작할 것을 예상하고 x
        // null이 들어오면 테스트도
    }

    // 이제 정상적인 케이스 실패한 케이스 다 맞는 것 같지만, 빈 문자열이 날라오는 때도 체크해보기
    @Test
    void failsHelloController() {
        HelloController helloController = new HelloController(name -> name);

        Assertions.assertThatThrownBy(() -> {
            helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);

        // null이 들어가서 예외가 발생하면 테스트 성공, 그렇지 않다면 실패(String이 왔다)
        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(NullPointerException.class);

        // 테스트가 빠르게 수행이 되면 더 많이 테스트 가능
    }

    // http 통신 때 도 예외 상황에 대한 테스트가 가능하다.
    @Test
    void failsHelloApi() {
        TestRestTemplate rest = new TestRestTemplate();

        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:9090/app/hello?name", String.class);

        // 이런 경우 500에러 보다는(Internal Server Error)
        // 서버에 정말 심각한 예외 상황, 프로그램에 버그가 있을 때 주로 사용하는 것
        // 웹 클라이언트가 요청정보를 잘못 줬다거나 보안상의 문제가 있을 때는 400번대 에러를 내는 것이 좋음
        // 그 응답 코드를 만들어 내려면 컨트롤러 부분을 조금 다른 방식으로 작성하거나 예외에다가 이 예외가
        // 던져 졌을 때는 이런 응답으로 해주세요, Spring MVC, dispatcherServlet에다가 알려줘야 하는데,
        // 그 단계 까지 나가면 깊이 들어가니깐
        // 요정도로 마무리
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
