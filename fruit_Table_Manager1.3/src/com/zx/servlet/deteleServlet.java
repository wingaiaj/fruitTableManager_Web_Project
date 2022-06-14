package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName deteleServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/13 20:27
 * @Version 1.0
 */
@WebServlet("/del.do")
public class deteleServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String fidStr = request.getParameter("fid");
        //判断获取参数是否为空
        if (StringUtils.isNotEmpty(fidStr)) {
            Integer fid = Integer.parseInt(fidStr);

                //调用fruitDao方法
                fruitDAO.deleteFruit(fid);

            //客户端重定向
            response.sendRedirect("index.html");
        }
    }
}
