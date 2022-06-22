package com.zx.myssmSpring.listener;

import com.zx.myssmSpring.ioc.BeanFactory;
import com.zx.myssmSpring.ioc.XmlClassPathApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @ClassName ContextLoaderListener
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/22 18:15
 * @Version 1.0
 */
//监听上下文启动，在上下文启动的时候去创建ioc容器，然后将其保存在application作用域
//后面中央控制器再从application作用域中获取ioc容器
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //获取application
        ServletContext servletContext = sce.getServletContext();
        //获取配置文件的参数
        String path = servletContext.getInitParameter("contextConfigLocation");
        //容器创建
        BeanFactory beanFactory = new XmlClassPathApplicationContext(path);
        //保存在作用域
        servletContext.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
