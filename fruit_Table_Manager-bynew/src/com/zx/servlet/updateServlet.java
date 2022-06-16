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
 * @ClassName updateServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/16 8:53
 * @Version 1.0
 */
@WebServlet("/update.do")
public class updateServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String fidStr = request.getParameter("fid");
        String fname = request.getParameter("fname");
        String priceStr = request.getParameter("price");
        String fCountStr = request.getParameter("fCount");
        String remark = request.getParameter("remark");
        if (StringUtilsZ.isNotEmpty(fidStr) && StringUtilsZ.isNotEmpty(priceStr) && StringUtilsZ.isNotEmpty(fCountStr)) {
            Integer fid = Integer.parseInt(fidStr);
            Integer price = Integer.parseInt(priceStr);
            Integer fCount = Integer.parseInt(fCountStr);
            boolean b = fruitDAO.updateFruit(new Fruit(fid, fname, price, fCount, remark));
            System.out.println(b);

        }
        //资源跳转
        response.sendRedirect("index.html");

    }
}
