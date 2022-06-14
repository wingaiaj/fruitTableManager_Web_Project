package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.jdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName IndexServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 19:29
 * @Version 1.0
 */
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取连接
            Connection connection = jdbcUtils.getConnection();
            //创建fruitDAO实现类
            FruitDAO fruitDAOImp = new FruitDAOImp();
            //获取所有水果记录
            List<Fruit> allFruit = fruitDAOImp.getAllFruit(connection);
            //创建session对象
            HttpSession session = request.getSession();
            //将allFruit保存到session作用域
            session.setAttribute("allFruit", allFruit);
            String viewName = "index";
            super.processTemplate(viewName, request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
