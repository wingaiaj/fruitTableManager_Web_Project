package com.zx.fruit.service;

import com.zx.fruit.bean.Fruit;

import java.util.List;

/**
 * @ClassName FruitService
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 9:38
 * @Version 1.0
 */
public interface FruitService {
    //获取所有数据 根据keyword pageNO
    List<Fruit> getFruitList(String keyword, Integer pageNO);

    //获取单条数据 根据fid
    Fruit getFruit(Integer fid);

    //删除数据 根据fid
    void delFruit(Integer fid);

    //更新数据
    void update(Fruit fruit);

    //获取总条数 根据 keyword
    Integer getCount(String keyword);

    //添加一条数据
    void add(Fruit fruit);
}
