package com.zx.bean;


import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.util.jdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName as
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 21:37
 * @Version 1.0
 */
public class as {
    public static void main(String[] args) {

        try {
            Connection connection = jdbcUtils.getConnection();
            FruitDAO fruitDAO = new FruitDAOImp();
            List<Fruit> allFruit = fruitDAO.getAllFruit(connection);
            System.out.println(Arrays.toString(allFruit.toArray()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
