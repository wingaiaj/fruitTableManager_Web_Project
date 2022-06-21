package com.zx.myssmSpring.myspringmvc;

import com.zx.myssmSpring.io.BeanFactory;
import com.zx.myssmSpring.io.XmlClassPathApplicationContext;
import com.zx.myssmSpring.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @ClassName DispatcherServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 11:57
 * @Version 1.0
 */
@WebServlet("*.do")//注册servlet
public class DispatcherServlet extends ViewBaseServlet {
    //接口实现类
    BeanFactory beanFactory; //声明getBean方法 根据 BeanId返回容器中的对象

    //实例化方法执行
    @Override
    public void init() throws ServletException {
        super.init();
        //实现接口   在实现类构造器中读取配置文件 获取对象  装入容器
        beanFactory = new XmlClassPathApplicationContext();

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
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
        Object beanValue = beanFactory.getBean(realPath);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DispatcherException("DispatcherServlet出现错误");
                }
            }
        }
    }
}
