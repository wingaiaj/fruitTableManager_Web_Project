package com.zx.util;

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
 * @Date 2022/6/15 17:52
 * @Version 1.0
 */
public class jdbcUtils {
    static DataSource dataSource = null;

    static {
        try {
            //当前类加载器 加载配置文件
            InputStream resourceAsStream = jdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //创建properties 对象
            Properties properties = new Properties();
            //读取配置文件
            properties.load(resourceAsStream);
            //DruidDataSourceFactory.createDataSource 加载配置文件 dataSource赋值
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接方法
    public static Connection getConnection() throws SQLException {
        //返回连接池连接
        return dataSource.getConnection();
    }

    //关闭连接和其他资源
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            //不为空关闭资源避免空指针
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
