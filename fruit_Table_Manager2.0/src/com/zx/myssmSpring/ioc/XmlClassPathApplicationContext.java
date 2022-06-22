package com.zx.myssmSpring.ioc;

import com.zx.myssmSpring.myspringmvc.DispatcherException;
import com.zx.myssmSpring.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName XmlClassPathApplicationContext
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/19 21:01
 * @Version 1.0
 */
public class XmlClassPathApplicationContext implements BeanFactory {
    private Map<String, Object> BeanMap = new HashMap<>();
    private String path = "applicationContext.xml";

    public XmlClassPathApplicationContext() {
        this("applicationContext.xml");
    }

    public XmlClassPathApplicationContext(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new RuntimeException("IOC容器的配置文件没有指定...");
        }
        try {
            //以下是 加载配置文件的操作
            //当前 类加载器读取配置文件 applicationContext.xml文件
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(path);
            //创建对象
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建第二个对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //解析配置文件 获取document
            Document document = documentBuilder.parse(resourceAsStream);
            //获取配置文件中的标签名
            NodeList beanList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanList.getLength(); i++) {
                //获取每个节点
                Node item = beanList.item(i);
                //判断节点类型 是否为 element
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    //强转为 element类型
                    Element element = (Element) item;
                    //element调用 方法 获取 id class
                    String id = element.getAttribute("id");
                    String ClassName = element.getAttribute("class");
                    //classname 通过反射获取 bean实例对象
                    Class aClass = Class.forName(ClassName);
                    //创建bean对象
                    Object classValue = aClass.newInstance();
                    //添加到map中
                    BeanMap.put(id, classValue);
                    //到目前为止,此处需要注意的是,bean和bean之间的依赖关系还未设置
                }
            }
            //组装bean之间的依赖关系
            for (int i = 0; i < beanList.getLength(); i++) {
                //拿到每一个bean对象
                Node beanItem = beanList.item(i);
                //判断节点类型 如果为element
                if (beanItem.getNodeType() == Node.ELEMENT_NODE) {
                    //强转为
                    Element beanElement = (Element) beanItem;
                    //获取beanid
                    String id = beanElement.getAttribute("id");
                    //获取所有子节点
                    NodeList childNodeList = beanElement.getChildNodes();
//                    System.out.println(childNodes.getLength());//到有依赖的第一个bean有5个子节点 前一个 后两个是空白 中间注释加property 第二个3个子节点
                    //遍历子节点
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Node beanChildNode = childNodeList.item(j);
                        //如果子节点是元素节点    并且此元素节点必须是"property"
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())) {
                            //强转为元素节点
                            Element propertyElement = (Element) beanChildNode;
                            //获取property元素的属性
                            String propertyName = propertyElement.getAttribute("name");
                            String refPropertyName = propertyElement.getAttribute("ref");
                            //通过ref找到对应propertyValue实例
                            Object propertyValue = BeanMap.get(refPropertyName);
                            //通过id找到对应bean对象
                            Object beanValue = BeanMap.get(id);
                            //给bean中的fruitDAO fruitService 赋值
                            Class beanClazz = beanValue.getClass();
                            //通过property属性名获取bean中同名对象
                            Field declaredField = beanClazz.getDeclaredField(propertyName);
                            //给属性赋值
                            declaredField.setAccessible(true);
//                            Object properValue = propertyValue.getClass().newInstance();
                            //给属性赋值  松耦合
                            declaredField.set(beanValue, propertyValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherException("DispatcherServlet出错了...");
        }
    }


    //根据beanId 获取 容器 中 的对象    在配置文件applicationContext.xml中的对象
    @Override
    public Object getBean(String BeanId) {
        return BeanMap.get(BeanId);
    }
}
