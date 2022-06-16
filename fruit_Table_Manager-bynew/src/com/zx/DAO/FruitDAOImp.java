package com.zx.DAO;

import com.zx.bean.Fruit;

import java.util.List;

/**
 * @ClassName FruitDAOImp
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 17:50
 * @Version 1.0
 */
public class FruitDAOImp extends BaseDAO<Fruit> implements FruitDAO {
    //获取所有水果数据
    @Override
    public List<Fruit> getAllFruit(Integer fid, String keyword) {
        String sql = "select fid,fname,price,fCount,remark from fruit where fname like ? or remark like ? limit ? ,5";
        return getData(sql, "%" + keyword + "%", "%" + keyword + "%", (fid - 1) * 5);
    }

    @Override
    public Fruit getFruit(int fid) {
        String sql = "select fid,fname,price,fCount,remark from fruit where fid = ?";
        return getOneData(sql, fid);
    }

    @Override
    public boolean addFruit(Fruit f) {
        String sql = "insert into fruit(0,fname,price,fCount,remark) values(?,?,?,?)";
        return update(sql, f.getFname(), f.getPrice(), f.getfCount(), f.getRemark());
    }

    @Override
    public boolean delFruit(Integer fid) {
        String sql = "delete from fruit where fid = ?";
        return update(sql, fid);
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        String sql = "update fruit set fname = ?,price = ?,fCount = ?,remark = ? where fid =?";
        return update(sql, fruit.getFname(), fruit.getPrice(), fruit.getfCount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public Long getAllItem(String keyword) {
        String sql = "select count(*) from fruit where fname like ? or remark like ?";
        Object[] item = getItem(sql, "%" + keyword + "%", "%" + keyword + "%");
        Long count = (Long) item[0];
        return count;
    }
}
