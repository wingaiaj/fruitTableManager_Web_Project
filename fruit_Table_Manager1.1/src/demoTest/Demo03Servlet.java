package demoTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName Demo01Servlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/12 15:01
 * @Version 1.0
 */
//测试session保存作用域(d3&d4)
@WebServlet("/demo03")
public class Demo03Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //设置session作用域
        session.setAttribute("uname","lili");
        //客户端重定向
        response.sendRedirect("demo04");
    }
}
