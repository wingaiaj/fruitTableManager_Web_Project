package com.zx.fruit.Controller;

import com.zx.fruit.Service.FruitService;
import com.zx.fruit.pojo.Fruit;
import com.zx.myssmSpring.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName FruitController
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/16 20:25
 * @Version 1.0
 */
public class FruitController  {
    //创建业务层实现类
    FruitService fruitService = null; //解耦合

    private String index(Integer pageNo, String keyword, String op, HttpSession session, HttpServletRequest request){
        if (pageNo == null) {
            pageNo = 1;
        }
        //创建session对象
        //判断是否是从表单提交
        //如果隐藏域 的值存在 且为opTrue
        if (StringUtils.isNotEmpty(op) && "opTrue".equals(op)) {
            //获取keyword
            //为空
            if (StringUtils.isEmpty(keyword)) {
                pageNo = 1;
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
        //获取水果总条数
        int Count = fruitService.getFruitCount(keyword);
        if (Count == 0) {
            pageNo = 1;
        }
        //再将pageNo保存session作用域中
        session.setAttribute("pageNO", pageNo);
        //获取水果记录
        List<Fruit> allFruit = fruitService.getFruit(keyword, pageNo);
        //将allFruit保存到session作用域
        session.setAttribute("allFruit", allFruit);
        //将总条数存入session作用域
        session.setAttribute("count", Count);
        //fix+index+suffix真实物理视图
        return "index";

    }

    private String add(String fname, Integer price, Integer count, String remark) {

        //获取参数
        fruitService.addFruit(new Fruit(0, fname, price, count, remark));

        //资源跳转 资源刷新
        return "redirect:fruit.do";
    }

    private String del(Integer fid){
        //获取参数
        //判断获取参数是否为空
        //调用fruitDao方法
        fruitService.del(fid);
        //客户端重定向
        return "redirect:fruit.do";

    }

    private String Edit(Integer fid, HttpServletRequest request)  {
        //获取请求参数
        //转换为int类型
        //调用根据节id获取水果方法
        Fruit fruit = fruitService.getOneFruit(fid);
        //将参数设置在请求作用域中
        request.setAttribute("fruit", fruit);
        //真实物理视图
        //相当于服务服务器内部转发一次
        return "edit";
    }

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark){
        //获取请求参数
        //创建水果对象
        Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
        //修改水果信息
        fruitService.update(fruit);
        //4.资源跳转
        //super.processTemplate("index",request,response);
        //request.getRequestDispatcher("index.html").forward(request,response);
        //此处需要重定向，目的是重新给IndexServlet发请求，重新获取fruitList，然后覆盖到session中，这样index.html页面上显示的session中的数据才是最新的
        return "redirect:fruit.do";
    }
}
