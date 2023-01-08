package hello.servlet.basic.request;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 1. 파라미더 전송기능
 * httpL//localhost:8080/request-param?username=kim&age=20*/
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        System.out.println("[total parameter get] - start");


        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> System.out.println(paramName + " = " + request.getParameter(paramName))
        );

        System.out.println("[total parameter get] - end");
        System.out.println();

        System.out.println("[single parameter get]");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[total parameter which have a same name]");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }

        response.getWriter().write("ok");
    }
}
