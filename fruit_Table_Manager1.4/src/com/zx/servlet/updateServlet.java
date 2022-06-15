package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName updateServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/13 14:38
 * @Version 1.0
 */
@WebServlet("/update.do")
public class updateServlet extends ViewBaseServlet {
    //实现FruitDAO子类
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取请求参数
        String fidStr = request.getParameter("fid");
        int fid = Integer.parseInt(fidStr);

        String fname = request.getParameter("fname");

        String priceStr = request.getParameter("price");
        int price = Integer.parseInt(priceStr);

        String fcountStr = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);

        String remark = request.getParameter("remark");
        //创建水果对象
        Fruit fruit = new Fruit(fid, fname, price, fcount, remark);

            //修改水果信息
           fruitDAO.alterFruit(fruit);

        //4.资源跳转
        //super.processTemplate("index",request,response);
        //request.getRequestDispatcher("index.html").forward(request,response);
        //此处需要重定向，目的是重新给IndexServlet发请求，重新获取fruitList，然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        response.sendRedirect("index.html");

    }
}
