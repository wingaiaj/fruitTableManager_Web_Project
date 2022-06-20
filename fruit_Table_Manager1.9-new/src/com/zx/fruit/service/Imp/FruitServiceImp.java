package com.zx.fruit.service.Imp;

import com.zx.fruit.DAO.FruitDAO;
import com.zx.fruit.bean.Fruit;
import com.zx.fruit.service.FruitService;

import java.util.List;

/**
 * @ClassName FruitServiceImp
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 9:39
 * @Version 1.0
 */
public class FruitServiceImp implements FruitService {
    FruitDAO fruitDAO = null; //松耦合

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNO) {
        return fruitDAO.getFruitLimit(pageNO, keyword);
    }

    @Override
    public Fruit getFruit(Integer fid) {
        return fruitDAO.getFruit(fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.deleteFruit(fid);
    }

    @Override
    public void update(Fruit fruit) {
        fruitDAO.alterFruit(fruit);
    }

    @Override
    public Integer getCount(String keyword) {
        return fruitDAO.getFruitCount(keyword);
    }

    @Override
    public void add(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }
}
