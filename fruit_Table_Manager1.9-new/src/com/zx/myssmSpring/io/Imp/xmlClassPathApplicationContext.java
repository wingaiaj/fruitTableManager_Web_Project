package com.zx.myssmSpring.io.Imp;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName xmlClassPathApplicationContext
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/20 9:50
 * @Version 1.0
 */
public class xmlClassPathApplicationContext implements BeanFactory {
    Map<String, Object> beanMap = new HashMap<>();

    public xmlClassPathApplicationContext() {
        try {
            //类加载器读取配置文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //文档构建工厂 创建documentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder类
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //加载配置文件
            //创建Document
            Document document = documentBuilder.parse(resourceAsStream);
            //获取元素
            NodeList bean = document.getElementsByTagName("bean");
            for (int i = 0; i < bean.getLength(); i++) {
                //获取每个 item
                Node item = bean.item(i);
                //判断节点类型是否为元素类型
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element item1 = (Element) item;
                    //获取
                    String id = item1.getAttribute("id");
                    String ClassName = item1.getAttribute("class");
                    //反射获取类的实例 创建类对象
                    Class ClassController = Class.forName(ClassName);
                    Object objBean = ClassController.newInstance();
                    //保存到集合
                    beanMap.put(id, objBean);
                }
            }
            //组装依赖关系
            for (int i = 0; i < bean.getLength(); i++) {
                //获取每个beanNode
                Node Bean = bean.item(i);
                //为元素节点
                if (Bean.getNodeType() == Node.ELEMENT_NODE) {
                    //转换为元素节点
                    Element BeanElement = (Element) Bean;
                    //获取当前 id  用来在map中取值
                    String id = BeanElement.getAttribute("id");
                    //获取所有子节点
                    NodeList childNodes = BeanElement.getChildNodes();
                    //遍历所有子节点
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node Child = childNodes.item(j);
                        //判断子节点是否是 元素节点 并且为 property
                        if (Child.getNodeType() == Node.ELEMENT_NODE && "property".equals(Child.getNodeName())) {
                            //转换为Element
                            Element elementChild = (Element) Child;
                            //获取属性值
                            String propertyName = elementChild.getAttribute("name");
                            String refProperty = elementChild.getAttribute("ref");
                            //通过相同的属性名 在map中获取属性实例
                            Object BeanValue = beanMap.get(refProperty);
                            //取出当前元素节点实例对象
                            Object Value = beanMap.get(id);
                            Class Clazz = Value.getClass();
                            //获取实例对象中声明的同名属性
                            Field declaredField = Clazz.getDeclaredField(propertyName);
                            //设置为可编辑
                            declaredField.setAccessible(true);
                            //最后给当前对象中声明的同名属性赋值为从map中获取的实例
                            declaredField.set(Value, BeanValue);
                            //
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
