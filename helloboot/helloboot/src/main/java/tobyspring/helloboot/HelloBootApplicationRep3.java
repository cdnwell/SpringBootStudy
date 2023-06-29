package tobyspring.helloboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HelloBootApplicationRep3 {

    public static void main(String[] args) {
        // 1. Tomcat servlet 웹서버 팩토리 변수 생성
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // 2. port 겹쳐서 새로 설정
        serverFactory.setPort(37700);
        // 3. WebServer 객체 생성, Tomcat servlet 웹서버 팩토리로 생성. getWebServer로 servletContext에 servlet 추가
        // 3.1 req에 parameter로 값 가져오기
        // 3.2 resp status 세팅 ok
        // 3.3 header에 content_type 설정
        // 3.4 writer로 content_type에 맞는 데이터 표현
        // 3.5 mapping 추가 /hello
        // 4. HttpServlet 인터페이스 service 구현. servletContext -> add mapping "/hello";
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("hello", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    String name = req.getParameter("name");

                    resp.setStatus(HttpStatus.OK.value());
                    resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                    resp.getWriter().println("Hello!, " + name);
                }
            }).addMapping("/hello");
        });
        webServer.start();
    }

}
