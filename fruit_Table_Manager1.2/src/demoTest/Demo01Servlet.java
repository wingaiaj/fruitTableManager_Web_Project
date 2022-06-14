package demoTest;

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
//测试request保存作用域(d1&d2)
@WebServlet("/demo01")
public class Demo01Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //向request作用域保存数据 在一次请求内
        request.setAttribute("uname","lili");
        //客户端重定向
        response.sendRedirect("demo02");//两次请求
        //服务器端内部转发
//        request.getRequestDispatcher("demo02").forward(request,response);
    }
}
