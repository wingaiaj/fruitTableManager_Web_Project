Servlet生命周期:实例化、初始化、服务、销毁
Servlet中的初始化方法有两个:init(),init(ServletConfig config)
带参数的代码如下
   public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }
另一个无参的代码如下
    public void init() throws ServletException {
    }
如果我们想要在Servlet中做一些准备工作，那我们可以重写init()方法
可以通过如下步骤去获取初始化数据
-获取config对象: ServletConfig config = getServletConfig();
-获取初始化参数值: config.getInitParameter(key)

-通过web.xml文件配置Servlet
 <servlet>
        <servlet-name>Servlet.Demo01Servlet</servlet-name>
        <servlet-class>com.zx.servlet.Servlet.Demo01Servlet</servlet-class>
        <init-param>
            <param-name>hello</param-name>
            <param-value>world</param-value>
        </init-param>
        <init-param>
            <param-name>uname</param-name>
            <param-value>tom</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>Servlet.Demo01Servlet</servlet-name>
        <url-pattern>/demo01</url-pattern>
    </servlet-mapping>


-通过注解方式进行配置:
@WebServlet(urlPatterns = {"/demo01"},
        initParams = {
                @WebInitParam(name = "hello", value = "world"),
                @WebInitParam(name = "uname", value = "tom")
        }
)
学习Servlet中的ServletContext和<context-param>
1)获取ServletContext，有很多方法
在初始化方法中:ServletContext servletContext = getServletContext();
在服务方法中也可以通过request对象获取,也可以通过session获取
req.getServletContext();
session.getServletContext();



