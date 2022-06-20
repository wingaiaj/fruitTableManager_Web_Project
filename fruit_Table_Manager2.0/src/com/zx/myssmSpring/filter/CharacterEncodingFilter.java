package com.zx.myssmSpring.filter;

import com.zx.myssmSpring.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName CharacterEncodingFilter
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 18:41
 * @Version 1.0
 */
@WebFilter(urlPatterns = {"*.do"}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8")})
public class CharacterEncodingFilter implements Filter {
    private String character = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取配置参数
        String encoding = filterConfig.getInitParameter("encoding");
        //如果encoding 不为空 character的值为encoding
        if (StringUtils.isNotEmpty(encoding)) {
            character = encoding;
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转换为http的请求调用setCharacterEncoding()设置编码
        ((HttpServletRequest) servletRequest).setCharacterEncoding(character);
        filterChain.doFilter(servletRequest, servletResponse);//放行操作
    }
}
