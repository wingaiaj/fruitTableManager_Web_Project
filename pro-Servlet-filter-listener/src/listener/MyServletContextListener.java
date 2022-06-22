package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @ClassName MyServletContextListener
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/22 17:56
 * @Version 1.0
 */
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("当前Servlet上下文对象初始化的动作被我监听到了....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Servlet上下文销毁的动作被我监听到了....");
    }
}
