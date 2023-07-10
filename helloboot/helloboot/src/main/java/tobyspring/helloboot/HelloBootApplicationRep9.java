package tobyspring.helloboot;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class HelloBootApplicationRep9 {

    public static void main(String[] args){
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        serverFactory.setPort(37700);
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
           servletContext.addServlet("frontcontroller", new HttpServlet() {
               HelloController helloController = new HelloController();

               @Override
               protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                   if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                       String name = req.getParameter("name");

                       String ret = helloController.hello(name);

                       // content type은 굳이 hello 안에서 할 필요는 없을 듯
                       // servlet에서 특별히 오류를 내지 않으면 200 OK 값을 자동으로 반환해 줌
                       // 굳이 안해도 되긴 함.
                       resp.setStatus(HttpStatus.OK.value());
                       resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                       resp.getWriter().println(ret);
                   } else if (req.getRequestURI().equals("/user")) {
                       resp.setStatus(HttpStatus.OK.value());
                   } else {
                       resp.setStatus(HttpStatus.NOT_FOUND.value());
                   }
               }
           }) .addMapping("/*");
        });
        webServer.start();
    }

}
