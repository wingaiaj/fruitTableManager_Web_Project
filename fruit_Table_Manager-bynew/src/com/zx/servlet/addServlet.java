package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.StringUtilsZ;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName addServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/16 8:18
 * @Version 1.0
 */
@WebServlet("/add.do")
public class addServlet extends ViewBaseServlet {
    //fruitDAO实现类
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转码
        request.setCharacterEncoding("utf-8");
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        String countStr = request.getParameter("fCount");
        String remark = request.getParameter("remark");
        if (StringUtilsZ.isNotEmpty(priceStr) && StringUtilsZ.isNotEmpty(countStr)) {
            Integer price = Integer.parseInt(priceStr);
            Integer count = Integer.parseInt(countStr);
            fruitDAO.addFruit(new Fruit(0, fname, price, count, remark));

        }
        //客户端重定向  第二次响应  刷新资源
        response.sendRedirect("index.html");
    }
}
