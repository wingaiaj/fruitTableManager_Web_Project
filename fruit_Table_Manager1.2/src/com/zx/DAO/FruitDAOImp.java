package com.zx.DAO;


import com.zx.bean.Fruit;
import com.zx.util.jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName FruitDAOImp
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 15:47
 * @Version 1.0
 */
public class FruitDAOImp extends BaseDAO<Fruit> implements FruitDAO {
    //添加一条记录
    @Override
    public boolean addFruit(Fruit fruit) {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "insert into fruit values(0,?,?,?,?)";
            return update(connection, sql, fruit.getfName(), fruit.getPrice(), fruit.getCount(), fruit.getRemark());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return false;
    }

    //获取所有水果数据
    @Override
    public List<Fruit> getAllFruit() {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "select fid,fname as 'fName',price,fCount as'count',remark from fruit";
            List<Fruit> listRecord = getListRecord(connection, sql);
            return listRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return null;
    }

    //比输入价格高的多条水果数据
    @Override
    public List<Fruit> getFruitList(Integer price) {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "select fid,fname as 'fName',price,fCount as 'count',remark from fruit where price < ?";
            List<Fruit> listRecord = getListRecord(connection, sql, price);
            return listRecord;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return null;
    }

    //修改指定水果数据根据id
    @Override
    public boolean alterFruit(Fruit fruit) {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "update fruit set fname=?,price=?,fCount=?,remark=? where fid = ?";
            boolean update = update(connection, sql, fruit.getfName(), fruit.getPrice(), fruit.getCount(), fruit.getRemark(), fruit.getFid());
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return false;
    }

    //获取指定名字水果数据
    @Override
    public Fruit getFruit(Integer fid) {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "select fid,fname as 'fName',price,fCount as 'count',remark from fruit where fid =?";
            Object record = getRecord(connection, sql, fid);
            return (Fruit) record;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return null;
    }

    @Override
    public boolean deleteFruit(Integer fid) {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
            String sql = "delete from fruit where fid =? ";
            return update(connection, sql, fid);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtils.close(connection, null, null);
        }
        return false;
    }
}
