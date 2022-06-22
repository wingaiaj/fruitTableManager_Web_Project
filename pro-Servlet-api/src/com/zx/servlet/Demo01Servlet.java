package com.zx.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
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
 * @Date 2022/6/19 16:00
 * @Version 1.0
 */
//注册servlet 并设置初始化参数
@WebServlet(urlPatterns = {"/demo01"},
        initParams = {
                @WebInitParam(name = "hello", value = "world"),
                @WebInitParam(name = "uname", value = "tom")
        }
)
public class Demo01Servlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //获取config参数
        ServletConfig servletConfig = getServletConfig();
        //获取web.xml中的配置参数
        String initValue = servletConfig.getInitParameter("hello"); // key hello -> value world
        System.out.println("initValue:" + initValue);
        ServletContext servletContext = getServletContext();//获取配置文件中 context-param   key contextConfigLocation -> value applicationContext.xml
        String initParameter = servletContext.getInitParameter("contextConfigLocation" );
        System.out.println("applicationContext:" + initParameter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        System.out.println(contextConfigLocation);
        HttpSession session = req.getSession();
        ServletContext servletContext1 = session.getServletContext();
        System.out.println(servletContext1);

    }
}
