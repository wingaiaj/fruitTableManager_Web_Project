package com.zx.myssmSpring;

import com.zx.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DispatcherServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 11:57
 * @Version 1.0
 */
@WebServlet("*.do")//注册servlet
public class DispatcherServlet extends ViewBaseServlet {
    //创建map集合
    Map<String, Object> BeanMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            //以下是 加载配置文件的操作
            //当前 类加载器读取配置文件 applicationContext.xml文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //创建对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建第二个对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //解析配置文件 获取document
            Document document = documentBuilder.parse(resourceAsStream);
            //获取配置文件中的标签名
            NodeList beanList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanList.getLength(); i++) {
                //获取每个节点
                Node item = beanList.item(i);
                //判断节点类型 是否为 element
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    //强转为 element类型
                    Element element = (Element) item;
                    //element调用 方法 获取 id class
                    String id = element.getAttribute("id");
                    String ClassName = element.getAttribute("class");
                    //classname 通过反射获取 实例对象
                    Class aClass = Class.forName(ClassName);
                    //创建对象
                    Object classValue = aClass.newInstance();
                    //添加到map中
                    BeanMap.put(id, classValue);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取servletPath
        String servletPath = request.getServletPath();
        //截取字符 例:/fruit.do -> fruit.do -> fruit
        String serPth = servletPath.substring(1);
        int pathDot = serPth.lastIndexOf(".do");
        String realPath = serPth.substring(0, pathDot);
        //获取请求返回的operate值
        String operate = request.getParameter("operate");
        //如果为空 默认值为index 访问index页面
        if (StringUtils.isEmpty(operate)) {
            operate = "index";
        }
        //从通过 key 从map中获取调用的Controller
        Object beanValue = BeanMap.get(realPath);
        //通过反射获取当前控制器中所有方法
        Method[] declaredMethods = beanValue.getClass().getDeclaredMethods(); // 改良控制器 松耦合
        //遍历所有方法
        for (Method m : declaredMethods) {
            //如果有此方法
            if (operate.equals(m.getName())) {
                //获取当前方法的参数名 数组
                Parameter[] parameters = m.getParameters();
                //创建可变形参数组 变量
                Object[] parametersValue = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    //获取参数类型的名称
                    String parameterTypeName = parameters[i].getType().getName();
                    //获取参数名
                    String parameterName = parameters[i].getName();
                    //如果参数名 为request 可变形参传入 request
                    if ("request".equals(parameterName)) {
                        parametersValue[i] = request;
                    } else if ("response".equals(parameterName)) {
                        parametersValue[i] = response;
                    } else if ("session".equals(parameterName)) {
                        parametersValue[i] = request.getSession();
                    } else {
                        //请求中获取参数
                        String realValue = request.getParameter(parameterName);
                        Object parObj = realValue;
                        //如果此参数为integer 强转
                        if (parObj != null) {
                            if ("java.lang.Integer".equals(parameterTypeName)) {
                                parObj = Integer.parseInt(realValue);
                            }
                        }
                        parametersValue[i] = parObj;
                    }
                }
                try {
                    //可变形参个数 传一个数组
                    m.setAccessible(true);
                    //获取返回字符串
                    String returnValue = (String) m.invoke(beanValue, parametersValue);
                    //资源跳转
                    if (returnValue.startsWith("redirect:")) {
                        response.sendRedirect(returnValue.substring("redirect:".length()));
                    } else {
                        super.processTemplate(returnValue, request, response);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
