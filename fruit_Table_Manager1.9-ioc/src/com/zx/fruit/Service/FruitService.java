package com.zx.fruit.Service;

import com.zx.fruit.pojo.Fruit;

import java.util.List;

/**
 * @ClassName FruitService
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 20:07
 * @Version 1.0
 */
public interface FruitService {
    //获取所有水果记录
    List<Fruit> getFruit(String keyword, Integer pageNo);

    //添加一条记录
    void addFruit(Fruit fruit);

    //获取单条水果记录根据fid
    Fruit getOneFruit(Integer fid);

    //获取总水果记录
    Integer getFruitCount(String keyword);

    //更新数据
    void update(Fruit fruit);

    //删除记录
    void del(Integer fid);
}
