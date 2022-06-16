package com.zx.servlet;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.util.StringUtilsZ;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName delServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 20:52
 * @Version 1.0
 */
@WebServlet("/del.do")
public class delServlet extends ViewBaseServlet {
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取fid参数
        String fidStr = request.getParameter("fid");
        Integer fid = null;
        //判断是否为空
        if (StringUtilsZ.isNotEmpty(fidStr)) {
            //转换为Integer类型 赋值
            fid = Integer.parseInt(fidStr);
        }
        //调用fruitDao方法 删除指定数据
        fruitDAO.delFruit(fid);

        //客户端重定向 响应给客户端 客户端再次发请求获取数据 更新资源

        response.sendRedirect("index.html");
    }
}
