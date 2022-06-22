package com.zx.myssmSpring.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @ClassName jdbcUtils
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 15:45
 * @Version 1.0
 */
public class jdbcUtils {
    static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static DataSource dataSource = null;

    //静态代码块加载和读取连接池配置文件 创建数据库连接池
    static {
        try {
            InputStream resourceAsStream = jdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConnection() throws SQLException {
        //本地线程获取连接
        Connection connection = threadLocal.get();
        //第一次连接赋值     连接为空
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        //不为空返回当前连接
        return threadLocal.get();
    }

    //关闭连接
    public static void closeConn() throws SQLException {
        //获取本地线程连接
        Connection connection = threadLocal.get();
        //如果为空结束方法
        if (connection == null) {
            return;
        }
        //如果不为空 关闭连接 本地线程为null
        if (!connection.isClosed()) {
            connection.close();
//            threadLocal.set(null);
            threadLocal.remove();
        }

    }


    //关闭资源
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            //不为空 再关闭资源 避免空指针
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}