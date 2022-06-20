package com.zx.myssmSpring.io.Imp;

/**
 * @ClassName BeanFactory
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 9:55
 * @Version 1.0
 */
public interface BeanFactory {
    //获取bean对象
    Object getBean(String id);
}
