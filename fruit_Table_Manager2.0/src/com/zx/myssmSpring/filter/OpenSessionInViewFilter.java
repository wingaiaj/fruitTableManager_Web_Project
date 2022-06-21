package com.zx.myssmSpring.filter;

import com.zx.myssmSpring.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @ClassName OpenSessionInViewFilter
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 20:33
 * @Version 1.0
 */
@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
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

        try {
            //开启事务
            System.out.println("开启事务...");
            TransactionManager.beginTrans();
            //放行操作
            filterChain.doFilter(servletRequest, servletResponse);
            //提交事务
            TransactionManager.commit();
            System.out.println("提交事务...");
        } catch (Exception e) {
            try {
            //回滚事务
                TransactionManager.rollback();
                System.out.println("回滚事务...");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        }
    }
}
