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
 * @ClassName editServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/16 8:48
 * @Version 1.0
 */
@WebServlet("/edit.do")
public class editServlet extends ViewBaseServlet {
    //创建fruitDAO实现类
    FruitDAO fruitDAO = new FruitDAOImp();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (StringUtilsZ.isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruit(fid);
            request.setAttribute("fruit", fruit);
        }
        super.processTemplate("edit", request, response);
    }
}
