package com.zx.myssmSpring.myssm;

import com.zx.myssmSpring.io.Imp.BeanFactory;
import com.zx.myssmSpring.io.Imp.xmlClassPathApplicationContext;
import com.zx.myssmSpring.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @ClassName DispatcherServlet
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/17 17:02
 * @Version 1.0
 */
@WebServlet("*.do")//接收以.do结尾的请求
public class DispatcherServlet extends ViewBaseServlet {

    //构造器在实例化时加载配置文件

    BeanFactory beanFactory = null;

    //在初始化方法中创建容器
    @Override
    public void init() throws ServletException {
        super.init();
        //创建xmlClasspathApplicationContext对象  内加载配置文件 和 controller
        beanFactory = new xmlClassPathApplicationContext();
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
        Object beanValue = beanFactory.getBean(substringPath);

        try {
            //反射获取组件中的方法
            Method[] declaredMethods = beanValue.getClass().getDeclaredMethods();
            for (Method m : declaredMethods) {
                //请求名和方法名匹配
                if (operate.equals(m.getName())) {
                    //获取此方法的所有参数
                    Parameter[] parameters = m.getParameters();
                    //创建谣传入的可变形参数组
                    Object[] parametersValue = new Object[parameters.length];
                    //填充参数
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        //获取参数名称
                        String name = parameter.getName();
                        //获取参数类型的名称
                        String TypeName = parameter.getType().getName();
                        //判断参数
                        if ("request".equals(name)) {
                            parametersValue[i] = request;
                        } else if ("response".equals(name)) {
                            parametersValue[i] = response;
                        } else if ("session".equals(name)) {
                            parametersValue[i] = request.getSession();
                        } else {
                            //从请求中获取参数
                            String parameterTrue = request.getParameter(name);

                            Object parObj = parameterTrue;
                            //第一次进来参数为空  integer pageNo==null 进去了 强转报空指针
                            if (parObj != null) {
                                if ("java.lang.Integer".equals(TypeName)) {
                                    //类型转换
                                    parObj = Integer.parseInt(parameterTrue);
                                }
                            }
                            //加入要传入的可变形参数组
                            parametersValue[i] = parObj;
                        }
                    }
                    //设置为可见
                    m.setAccessible(true);
                    //调用方法
                    Object methodReturn = m.invoke(beanValue, parametersValue);
                    //视图操作
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
