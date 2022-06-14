package demoTest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName Demo01Servlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/12 15:01
 * @Version 1.0
 */
//测试application保存作用域(d5&d6)
@WebServlet("/demo05")
public class Demo05Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = request.getServletContext();
        //设置application作用域 在服务器运行时都可以获取
        application.setAttribute("uname","lili");
        //客户端重定向
        response.sendRedirect("demo06");
    }
}
