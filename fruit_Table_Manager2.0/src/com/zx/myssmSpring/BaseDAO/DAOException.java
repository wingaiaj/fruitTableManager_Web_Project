package com.zx.myssmSpring.BaseDAO;

/**
 * @ClassName DAOException
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/21 20:57
 * @Version 1.0
 */
public class DAOException extends RuntimeException {
    public DAOException(String msg) {
        super(msg);
    }
}
