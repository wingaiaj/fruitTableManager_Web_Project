package demoTest;

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
//测试request保存作用域(d1&d2)
@WebServlet("/demo02")
public class Demo02Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     //获取request保存作用域保存的数据，key为uname
        Object uname = request.getAttribute("uname");
        System.out.println(uname);
    }
}
