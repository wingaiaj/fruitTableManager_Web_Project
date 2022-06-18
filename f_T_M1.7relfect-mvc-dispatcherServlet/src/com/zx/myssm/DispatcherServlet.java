package com.zx.myssm;

import com.zx.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DispatcherServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/17 17:02
 * @Version 1.0
 */
@WebServlet("*.do")//接收以.do结尾的请求
public class DispatcherServlet extends HttpServlet {

    Map<String, Object> beanMap = new HashMap<>();

    //构造器在实例化时加载配置文件
    public DispatcherServlet() {

    }

    @Override
    public void init() throws ServletException {
        try {
            //类加载器读取配置文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //文档构建工厂 创建documentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder类
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //加载配置文件
            //创建Document
            Document document = documentBuilder.parse(resourceAsStream);
            //获取元素
            NodeList bean = document.getElementsByTagName("bean");
            for (int i = 0; i < bean.getLength(); i++) {
                //获取每个 item
                Node item = bean.item(i);
                //判断节点类型是否为元素类型
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element item1 = (Element) item;
                    //获取
                    String id = item1.getAttribute("id");
                    String ClassName = item1.getAttribute("class");
                    //反射获取类的实例 创建类对象
                    Class ClassController = Class.forName(ClassName);
                    Object objBean = ClassController.newInstance();
                    //
                    Method declaredMethod = ClassController.getDeclaredMethod("setServletContext", ServletContext.class);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(objBean, this.getServletContext());
                    //保存到集合
                    beanMap.put(id, objBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取请求路径名    /fruit.do
        String servletPath = request.getServletPath();
        System.out.println(servletPath);
        //截取请求路径名
        String substring = servletPath.substring(1);
        System.out.println(substring);
        //获取.do第一次出现的索引
        int indexDo = substring.lastIndexOf(".do");
        //获取截取请求路径之后的 字符 fruit
        String substringPath = substring.substring(0, indexDo);
        System.out.println(substringPath);
        //从请求参数中获取operate的value
        String operate = request.getParameter("operate");
        //判断value是否为空
        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }
        //map中根据请求名获取请求的组件
        Object beanValue = beanMap.get(substringPath);

        try {
            //反射获取组件中的方法
            Method declaredMethod = beanValue.getClass().getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            //设置为可调用
            declaredMethod.setAccessible(true);
            //调用方法
            declaredMethod.invoke(beanValue, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //抛出异常
        new RuntimeException("参数异常");

    }
}
