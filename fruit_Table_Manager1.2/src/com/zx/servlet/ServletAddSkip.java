package com.zx.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ServletAddSkip
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/14 15:03
 * @Version 1.0
 */
@WebServlet("/AddSkip.do")
public class ServletAddSkip extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //资源跳转
        super.processTemplate("add", request, response);
    }
}
