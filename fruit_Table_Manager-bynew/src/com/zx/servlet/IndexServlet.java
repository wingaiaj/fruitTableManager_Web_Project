package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.StringUtilsZ;

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
 * @Date 2022/6/15 19:58
 * @Version 1.0
 */
@WebServlet("/index.html")
public class IndexServlet extends ViewBaseServlet {
    //实现DAO
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符集
        request.setCharacterEncoding("utf-8");
        //获取session
        HttpSession session = request.getSession();
        Integer pageNo = 1;
        String keyword = null;
        String op = request.getParameter("op");
        //获取参数判断是否是表单提交
        if (StringUtilsZ.isNotEmpty(op) && "opTrue".equals(op)) {
//            pageNo = 1;
            //获取文本框输入参数
            keyword = request.getParameter("keyword");
            //如果为空 keyword为""
            if (StringUtilsZ.isEmpty(keyword)) {
                keyword = "";
            }
            //不为空 保存再session作用域中
            session.setAttribute("keyword", keyword);
        } else {
            //获取pg参数
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtilsZ.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);
            }

            Object keyword1 = session.getAttribute("keyword");
            if (keyword1 != null) {
                keyword = (String) keyword1;
            }
            if (StringUtilsZ.isEmpty(keyword)) {
                keyword = "";
            }
        }
        //保存再session作用域
        session.setAttribute("pageNo", pageNo);
        //获取所有水果记录
        List<Fruit> allFruit = fruitDAO.getAllFruit(pageNo, keyword);
        //总记录条数
        Long allItem = fruitDAO.getAllItem(keyword);
        //保存到session作用域
        session.setAttribute("allFruit", allFruit);
        session.setAttribute("allItem", (allItem + 5 - 1) / 5);
        //获取物理视图
        super.processTemplate("index", request, response);
    }
}
