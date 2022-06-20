package com.zx.myssmSpring.trans;

import com.zx.myssmSpring.util.jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName TarnsactionManager
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 20:36
 * @Version 1.0
 */
public class TransactionManager {

    ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    //开启事务
    public void beginTrans() throws SQLException {
        //本地线程获取连接
        Connection connection = threadLocal.get();
        //连接为空
        if (connection == null) {
            connection = jdbcUtils.getConnection();
            threadLocal.set(connection);
        }
        //不为空
        //取消自动提交
        connection.setAutoCommit(false);
    }

    //提交事务
    public void commit() throws SQLException {

        jdbcUtils.getConnection().commit();
    }

    //回滚事务
    public void rollback() throws SQLException {
        //本地线程获取连接
        Connection connection = threadLocal.get();
        //连接为空
        if (connection == null) {
            connection = jdbcUtils.getConnection();
        }
        //回滚
        connection.rollback();
    }
}
