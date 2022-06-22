package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ClassName Demo01Filter
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 17:40
 * @Version 1.0
 */
//拦截
@WebFilter("*.do")
public class Filter03 implements Filter {
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
        System.out.println("C");
        filterChain.doFilter(servletRequest, servletResponse);//放行操作
        System.out.println("C2");
    }
}
