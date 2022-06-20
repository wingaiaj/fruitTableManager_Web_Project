package com.zx.fruit.pojo;

/**
 * @ClassName Fruit
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 16:58
 * @Version 1.0
 */
public class Fruit {
    private Integer fid;
    private String fName;
    private Integer price;
    private Integer count;
    private String remark;

    public Fruit() {
    }

    public Fruit(Integer fid, String fName, Integer price, Integer count, String remark) {
        this.fid = fid;
        this.fName = fName;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fid=" + fid +
                ", fName='" + fName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                '}';
    }
}
