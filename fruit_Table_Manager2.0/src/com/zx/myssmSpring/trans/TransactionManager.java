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

    //开启事务
    public static void beginTrans() throws SQLException {
        //开启事务
        //本地线程获取连接
        jdbcUtils.getConnection().setAutoCommit(false);//取消自动提交
    }

    //提交事务
    public static void commit() throws SQLException {
        //获取连接
        Connection connection = jdbcUtils.getConnection();
        //提交操作
        connection.commit();
        //关闭连接
        jdbcUtils.closeConn();
    }

    //回滚事务
    public static void rollback() throws SQLException {
        //获取连接
        Connection connection = jdbcUtils.getConnection();
        //回滚操作
        connection.rollback();
        //关闭连接
        jdbcUtils.closeConn();
    }
}
