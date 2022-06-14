package com.zx.DAO;


import com.zx.bean.Fruit;

import java.util.List;

/**
 * @ClassName FruitDAO
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 15:48
 * @Version 1.0
 */
public interface FruitDAO {
    //添加水果记录操作
    boolean addFruit(Fruit fruit);

    //查询所有水果数据
    List<Fruit> getAllFruit();

    //根据价格查询多条水果记录
    List<Fruit> getFruitList(Integer price);

    //修改水果数据根据水果id
    boolean alterFruit(Fruit fruit);

    //查询通过fid单条水果数据
    Fruit getFruit(Integer fid);

    //指定id删除记录
    boolean deleteFruit(Integer fid);

}
