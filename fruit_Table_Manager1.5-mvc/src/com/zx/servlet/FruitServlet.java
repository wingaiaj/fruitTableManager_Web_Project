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
 * @ClassName FruitServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/16 20:25
 * @Version 1.0
 */
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    //创建fruitDAO实现类
    FruitDAO fruitDAO = new FruitDAOImp();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        String operate = request.getParameter("operate");
        //如果为空 默认值为index 访问index页面
        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }

        switch (operate) {
            case"index":
                index(request,response);
                break;
            case "del":
                del(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "addSkip":
                addSkip(request, response);
                break;
            case "edit":
                Edit(request, response);
                break;
            default:
                processTemplate("error",request,response);//防止恶意拼接operate
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageNo = 1;
        //创建session对象
        HttpSession session = request.getSession();
        //判断是否是从表单提交
        String op = request.getParameter("op");
        String keyword = null;
        //如果隐藏域 的值存在 且为opTrue
        if (StringUtils.isNotEmpty(op) && "opTrue".equals(op)) {
            //获取keyword
            keyword = request.getParameter("keyword");
            //为空
            if (StringUtils.isEmpty(keyword)) {
                //赋值为”“
                keyword = "";
            }
            //保存到session作用域
            session.setAttribute("keyword", keyword);
        } else {//不为表单提交
            //获取当页参数
            String pageNoStr = request.getParameter("pageNo");
            //判断是否为空
            if (StringUtils.isNotEmpty(pageNoStr)) {
                //赋值
                pageNo = Integer.parseInt(pageNoStr);
            }
            //获取作用域中的keyword
            Object objKeyword = session.getAttribute("keyword");
            //如果不等于null
            if (objKeyword != null) {
                keyword = (String) objKeyword;
            } else {
                keyword = "";
            }
        }

        //再将pageNo保存session作用域中
        session.setAttribute("pageNO", pageNo);

        //获取水果记录
        List<Fruit> allFruit = fruitDAO.getFruitLimit(pageNo, keyword);
        //将allFruit保存到session作用域
        session.setAttribute("allFruit", allFruit);

        //获取水果总条数
        int Count = fruitDAO.getFruitCount(keyword);

        //将总条数存入session作用域
        session.setAttribute("count", (Count + 8 - 1) / 8);

        //fix+index+suffix真实物理视图
        super.processTemplate("index", request, response);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        response.sendRedirect("fruit.do");
    }

    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String fidStr = request.getParameter("fid");
        //判断获取参数是否为空
        if (StringUtils.isNotEmpty(fidStr)) {
            Integer fid = Integer.parseInt(fidStr);

            //调用fruitDao方法
            fruitDAO.deleteFruit(fid);

            //客户端重定向
            response.sendRedirect("fruit.do");
        }
    }

    private void Edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        String fidStr = request.getParameter("fid");
        if (StringUtils.isNotEmpty(fidStr)) {
            //转换为int类型
            int fid = Integer.parseInt(fidStr);
            //调用根据节id获取水果方法
            Fruit fruit = fruitDAO.getFruit(fid);
            //将参数设置在请求作用域中
            request.setAttribute("fruit", fruit);
            //真实物理视图
            super.processTemplate("edit", request, response);//相当于服务服务器内部转发一次

        }
    }

    private void addSkip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //资源跳转
        super.processTemplate("add", request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        response.sendRedirect("fruit.do");

    }
}
