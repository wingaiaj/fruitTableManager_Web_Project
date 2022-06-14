package demoTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName Demo02Servlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/12 15:05
 * @Version 1.0
 */
//测试session保存作用域(d3&d4)
@WebServlet("/demo04")
public class Demo04Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //获取当前session作用域的值  在一次session会话中有用
        Object uname = session.getAttribute("uname");
        System.out.println(uname);
    }
}
