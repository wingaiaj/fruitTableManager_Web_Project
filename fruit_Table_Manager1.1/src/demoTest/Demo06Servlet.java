package demoTest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName Demo02Servlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/12 15:05
 * @Version 1.0
 */
//测试application保存作用域(d5&d6)
@WebServlet("/demo06")
public class Demo06Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = request.getServletContext();
        Object uname = application.getAttribute("uname");
        System.out.println("uname = "+uname);
    }
}
