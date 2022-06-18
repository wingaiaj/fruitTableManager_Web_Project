package com.zx.controller;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.bean.Fruit;
import com.zx.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
public class FruitController {
    //创建fruitDAO实现类
    FruitDAO fruitDAO = new FruitDAOImp();

    private String index(String op, String keyword, Integer pageNo, HttpServletRequest request) {
        if (pageNo == null) {
            pageNo = 1;
        }
        //创建session对象
        HttpSession session = request.getSession();
        if (StringUtils.isNotEmpty(op) && "opTrue".equals(op)) {
            //为空
            if (StringUtils.isEmpty(keyword)) {
                //赋值为”“
                keyword = "";
            }
            //保存到session作用域
            session.setAttribute("keyword", keyword);
        } else {//不为表单提交
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

        int Count = fruitDAO.getFruitCount(keyword);

        session.setAttribute("count", (Count + 8 - 1) / 8);

        return "index";

    }

    private String add(String fname, Integer price, Integer count, String remark) {

        //调用addFruit方法
        fruitDAO.addFruit(new Fruit(0, fname, price, count, remark));
        //资源跳转 资源刷新
        return "redirect:fruit.do";
    }

    private String del(Integer fid) throws IOException {
        //判断获取参数是否为空
        if (fid != null) {
            //调用fruitDao方法
            fruitDAO.deleteFruit(fid);
            //客户端重定向
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String Edit(Integer fid, HttpServletRequest request) {
        //获取请求参数
        if (fid != null) {
            //转换为int类型
            //调用根据节id获取水果方法
            Fruit fruit = fruitDAO.getFruit(fid);
            //将参数设置在请求作用域中
            request.setAttribute("fruit", fruit);
            //真实物理视图
//            super.processTemplate("edit", request, response);//相当于服务服务器内部转发一次/
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {

        //获取请求参数
        //创建水果对象
        Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
        //修改水果信息
        fruitDAO.alterFruit(fruit);
        //4.资源跳转
        //super.processTemplate("index",request,response);
        //request.getRequestDispatcher("index.html").forward(request,response);
        //此处需要重定向，目的是重新给IndexServlet发请求，重新获取fruitList，然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        return "redirect:fruit.do";

    }
}
