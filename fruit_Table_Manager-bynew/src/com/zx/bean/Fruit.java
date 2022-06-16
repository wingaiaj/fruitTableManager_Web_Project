package com.zx.bean;

/**
 * @ClassName Fruit
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 17:47
 * @Version 1.0
 */
public class Fruit {
    Integer fid;
    String fname;
    Integer price;
    Integer fCount;
    String remark;

    public Fruit() {
    }

    public Fruit(Integer fid, String fname, Integer price, Integer fCount, String remark) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.fCount = fCount;
        this.remark = remark;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getfCount() {
        return fCount;
    }

    public void setfCount(Integer fCount) {
        this.fCount = fCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
