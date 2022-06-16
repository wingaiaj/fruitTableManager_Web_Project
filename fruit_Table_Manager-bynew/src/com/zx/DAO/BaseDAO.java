package com.zx.DAO;

import com.zx.util.jdbcUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseDAO
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 17:49
 * @Version 1.0
 */
public class BaseDAO<T> {
    Class<T> clazz = null;
    Connection connection = null;

    //当子类实例化时 获取父类泛型参数
    {
        try {
            connection = jdbcUtils.getConnection();
            Type genericSuperclass = this.getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            //类型转换给clazz赋值
            clazz = (Class<T>) actualTypeArguments[0];
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取多条数据
    public List<T> getData(String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            //设置占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql语句返回结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //创建集合
            ArrayList<T> arrayList = new ArrayList();
            //判断结果集是否有数据
            while (resultSet.next()) {
                //创建对象
                T t = clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    //获取列名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //获取列值
                    Object columnValues = resultSet.getObject(columnLabel);
                    //反射获取对象
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    //设置为可变
                    declaredField.setAccessible(true);
                    //设置对象值
                    declaredField.set(t, columnValues);
                }
                //添加到集合
                arrayList.add(t);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            jdbcUtils.close(null, preparedStatement, resultSet);
        }
        return null;
    }

    //获取单条数据
    public T getOneData(String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            //设置占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql返回结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //有数据
            if (resultSet.next()) {
                //创建对象
                T t = clazz.newInstance();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    //获取列值
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取列名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //获取对象属性
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    //设置为可变
                    declaredField.setAccessible(true);
                    //给对象赋值
                    declaredField.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(null, preparedStatement, resultSet);
        }
        return null;
    }

    //update操作
    public boolean update(String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        try {
            //预编译sql语句
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(null, preparedStatement, null);
        }
        return false;
    }

    public Object[] getItem( String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql 获取结果集
            resultSet = preparedStatement.executeQuery();
            //结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取总列数
            int columnCount = metaData.getColumnCount();
            //创建数组
            Object[] objects = null;
            //判断是否有数据 一条数据
            if (resultSet.next()) {
                //有数据再创建对象
                objects = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    //获取列名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //获取列值 返回的是一条记录 查询的是记录条数
                    Object value = resultSet.getObject(columnLabel);
                    objects[i] = value;
                }
            }
            //返回 记录数组
            return objects;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(null, preparedStatement, resultSet);
        }
        return null;
    }

}
