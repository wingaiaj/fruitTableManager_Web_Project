package com.zx.bean;

/**
 * @ClassName Fruit
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/11 16:58
 * @Version 1.0
 */
public class Fruit {
    String fName;
    int price;
    int count;
    String remark;

    public Fruit() {
    }

    public Fruit(String fName, int price, int count, String remark) {
        this.fName = fName;
        this.price = price;
        this.count = count;
        this.remark = remark;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
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
                "fName='" + fName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", remark='" + remark + '\'' +
                '}';
    }
}
