package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName Servlet.Demo01Servlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 17:32
 * @Version 1.0
 */
@WebServlet("/demo01.do")
public class Demo01Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("demo01Service.....");
        request.getRequestDispatcher("succ.html").forward(request, response);
    }
}
