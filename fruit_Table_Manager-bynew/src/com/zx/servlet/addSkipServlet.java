package com.zx.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName addSkipServlet`
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 21:22
 * @Version 1.0
 */
@WebServlet("/addSkip.do")
public class addSkipServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //跳转到add页面
        super.processTemplate("add",request,response);
    }
}
