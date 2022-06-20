package com.zx.myssmSpring.io;

/**
 * @ClassName BeanFactory
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 20:57
 * @Version 1.0
 */
public interface BeanFactory {
    //根据beanID获取bean对象
    Object getBean(String BeanId);
}
