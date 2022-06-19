package com.zx.myssm;

import com.zx.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
public class DispatcherServlet extends ViewBaseServlet {

    Map<String, Object> beanMap = new HashMap<>();

    //构造器在实例化时加载配置文件
    public DispatcherServlet() {

    }

    @Override
    public void init() throws ServletException {
        super.init();
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
        //截取请求路径名
        String substring = servletPath.substring(1);
        //获取.do第一次出现的索引
        int indexDo = substring.lastIndexOf(".do");
        //获取截取请求路径之后的 字符 fruit
        String substringPath = substring.substring(0, indexDo);
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
            Method[] declaredMethods = beanValue.getClass().getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (operate.equals(m.getName())) {
                    Parameter[] parameters = m.getParameters();
                    Object[] parametersValue = new Object[parameters.length];

                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String name = parameter.getName();
                        String TypeName = parameter.getType().getName();
                        if ("request".equals(name)) {
                            parametersValue[i] = request;
                        } else if ("response".equals(name)) {
                            parametersValue[i] = response;
                        } else if ("session".equals(name)) {
                            parametersValue[i] = request.getSession();
                        } else {
                            String parameterTrue = request.getParameter(name);
                            Object parObj = parameterTrue;
                            if (parObj != null) {
                                if ("java.lang.Integer".equals(TypeName)) {
                                    parObj = Integer.parseInt(parameterTrue);
                                }
                            }
                            parametersValue[i] = parObj;
                        }
                    }
                    m.setAccessible(true);
                    Object methodReturn = m.invoke(beanValue, parametersValue);
                    //不为空 强转为 String
                    if (methodReturn != null) {
                        //将返回值转为 string
                        String methodReturnValue = (String) methodReturn;
                        //通过返回的字符串 判断资源跳转的方式
                        if (methodReturnValue.startsWith("redirect:")) { //例 redirect:fruit.do
                            //截取字符为 fruit.do
                            String redirect = methodReturnValue.substring("redirect:".length());
                            //资源重定向
                            response.sendRedirect(redirect);
                        } else {//不为redirect 开头
                            //跳转资源 "edit"
                            super.processTemplate(methodReturnValue, request, response);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //抛出异常
        new RuntimeException("参数异常");

    }
}
