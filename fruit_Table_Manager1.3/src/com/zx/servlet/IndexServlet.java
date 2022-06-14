package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName IndexServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 19:29
 * @Version 1.0
 */
@WebServlet("/index.html")
public class IndexServlet extends ViewBaseServlet {
        //创建fruitDAO实现类
        FruitDAO fruitDAOImp = new FruitDAOImp();
        Integer pageNo = 1;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当页参数
        String pageNoStr = request.getParameter("pageNo");
        //判断是否为空
        if (StringUtils.isNotEmpty(pageNoStr)) {
            //赋值
            pageNo = Integer.parseInt(pageNoStr);
        }
        //创建session对象
        HttpSession session = request.getSession();

        //将pageNo保存再session作用域中
        session.setAttribute("pageNO", pageNo);

        //获取水果记录
        List<Fruit> allFruit = fruitDAOImp.getFruitLimit(pageNo);
        //将allFruit保存到session作用域
        session.setAttribute("allFruit", allFruit);

        //获取水果总条数
        int Count = fruitDAOImp.getFruitCount();

        //将总条数存入session作用域
        session.setAttribute("count",(Count+8-1)/8);

        //fix+index+suffix真实物理视图
        super.processTemplate("index", request, response);

    }
}
