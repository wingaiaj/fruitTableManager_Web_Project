package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AddServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/14 8:23
 * @Version 1.0
 */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    //创建fruitDAO实现类
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取参数
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        String countStr = request.getParameter("fcount");
        String remark = request.getParameter("remark");
        if (StringUtils.isNotEmpty(priceStr) && StringUtils.isNotEmpty(countStr)) {
            Integer price = Integer.parseInt(priceStr);
            Integer count = Integer.parseInt(countStr);
                //调用addFruit方法
                fruitDAO.addFruit(new Fruit(0, fname, price, count, remark));
        }
        //资源跳转 资源刷新
        response.sendRedirect("index.html");
    }
}
