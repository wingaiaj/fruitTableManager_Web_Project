package com.zx.bean;

import com.zx.DAO.FruitDAO;
import com.zx.DAO.FruitDAOImp;
import com.zx.util.jdbcUtils;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName test
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/13 18:17
 * @Version 1.0
 */
//测试值
public class test {
    public static void main(String[] args) {
        try {
            Connection connection = jdbcUtils.getConnection();
            FruitDAO dao = new FruitDAOImp();
            List<Fruit> allFruit = dao.getAllFruit();
            System.out.println(Arrays.toString(allFruit.toArray()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}