package Servlet;

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
 * @Date 2022/6/20 17:52
 * @Version 1.0
 */
@WebServlet("/demo02.do")
public class Demo02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("demo02Service....");
        request.getRequestDispatcher("succ.html").forward(request, response);
    }
}
