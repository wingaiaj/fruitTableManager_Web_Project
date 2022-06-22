package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName Demo01Filter
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 17:40
 * @Version 1.0
 */
//拦截
//@WebFilter("*.do")
public class Demo01Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("helloA");
        filterChain.doFilter(servletRequest, servletResponse);//放行操作
        System.out.println("helloA2");
    }
}
