package com.zx.DAO;

import com.zx.bean.Fruit;

import java.util.List;

/**
 * @ClassName FruitDAO
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 17:50
 * @Version 1.0
 */
public interface FruitDAO {
    //获取所有水果
    List<Fruit> getAllFruit(Integer integer,String keyword);

    //根据fid获取指定水果
    Fruit getFruit(int fid);

    //添加水果
    boolean addFruit(Fruit f);

    //删除记录
    boolean delFruit(Integer fid);

    //修改
    boolean updateFruit(Fruit fruit);

    //获取总条数
    Long getAllItem(String keyword);
}
