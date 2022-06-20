package com.zx.fruit.Service.impl;

import com.zx.fruit.Service.FruitService;
import com.zx.fruit.DAO.FruitDAO;
import com.zx.fruit.pojo.Fruit;

import java.util.List;

/**
 * @ClassName FruitServiceImp
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 20:13
 * @Version 1.0
 */
//业务层
public class FruitServiceImp implements FruitService {
    //创建DAO实现类
    FruitDAO fruitDAO = null; //解耦合

    //获取所有分页后的数据
    @Override
    public List<Fruit> getFruit(String keyword, Integer pageNo) {
        return fruitDAO.getFruitLimit(pageNo, keyword);
    }

    //添加一条记录
    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    //获取单个水果对象
    @Override
    public Fruit getOneFruit(Integer fid) {
        return fruitDAO.getFruit(fid);
    }

    //获取总记录条数
    @Override
    public Integer getFruitCount(String keyword) {
        int fruitCount = fruitDAO.getFruitCount(keyword);
        return (fruitCount + 8 - 1) / 8;
    }

    //更新数据
    @Override
    public void update(Fruit fruit) {
        fruitDAO.alterFruit(fruit);
    }

    @Override
    public void del(Integer fid) {
        fruitDAO.deleteFruit(fid);
    }
}
