# Spring-Cloud-Zuul


<nav>
<a href="#一简介">一、简介</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-API-网关">1.1 API 网关</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-Spring-Cloud-Zuul">1.2 Spring Cloud Zuul</a><br/>
<a href="#二项目结构">二、项目结构</a><br/>
<a href="#三Zuul-网关">三、Zuul 网关</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#31-引入依赖">3.1 引入依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#32-添加注解">3.2 添加注解</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#33--项目配置">3.3  项目配置</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#34--启动服务">3.4  启动服务</a><br/>
<a href="#四错误熔断">四、错误熔断</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#41--默认依赖">4.1  默认依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#42-服务降级">4.2 服务降级</a><br/>
<a href="#五Zuul--过滤器">五、Zuul  过滤器</a><br/>
<a href="#六负载均衡">六、负载均衡</a><br/>
<a href="#七常见异常">七、常见异常</a><br/>
</nav>

## 一、简介

### 1.1 API 网关

API 网关是整个微服务系统的门面，所有的外部访问需要通过网关进行调度和过滤。它实现了请求转发、负载均衡、校验过滤、错误熔断、服务聚合等功能：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/apiGateway.png"/> </div>


### 1.2 Spring Cloud Zuul

Spring Cloud 基于 Net Flix Zuul 实现了网关组件，这就是 Spring Cloud Zuul。它除了实现负载均衡、错误熔断、路由转发等功能，还能与 Spring 的其他组件无缝配合使用。



## 二、项目结构

[spring-cloud-feign](https://github.com/heibaiying/spring-samples-for-all/tree/master/spring-cloud/spring-cloud-feign) 用例已经通过 Feign 实现服务间的调用，且提供了两个服务单元 (consumer、producer)，可以方便直观地测试 Zuul 的路由、负载均衡、和错误熔断等功能，所以本用例在其基础上进行整合。

+ **common**：公共的接口和实体类；
+ **consumer**：服务的消费者，采用 Feign 调用产品服务；
+ **producer**：服务的提供者；
+ **eureka**：注册中心；
+ **zuul**：API 网关。

聚合项目目录如下：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/spring-cloud-zuul.png"/> </div>


Zuul 项目目录如下：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul.png"/> </div>



## 三、Zuul 网关

### 3.1 引入依赖

主要的依赖是 spring-cloud-starter-netflix-zuul：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.8.RELEASE</version>
        <relativePath/>
    </parent>

    <artifactId>zuul</artifactId>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Finchley.SR2</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!--zuul-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

### 3.2 添加注解

在启动类上添加 @EnableZuulProxy和 @EnableDiscoveryClient 注解，@EnableZuulProxy 会自动设置 Zuul 服务器端点并在其中开启一些反向代理过滤器，以便将请求转发到后端服务器：

```java
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
```

### 3.3  项目配置

在配置文件中指定注册中心的地址并配置网关的路由规则。Zuul 需要指定注册中心的地址，Zuul 会从 Eureka 获取其他微服务的实例信息，然后按照指定的路由规则进行请求转发：

```yaml
server:
  port: 8090
# 指定服务命名
spring:
  application:
    name: zuul
# 指定注册中心地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8010/eureka/
# 网关的路由
zuul:
  routes:
    xxxx: #这个地方的值是可以任意的字符串
      path: /producer/**
      serviceId: producer
    consumer:
      path: /consumer/**
      serviceId: consumer
```

### 3.4  启动服务

启动 eureka、producer、consumer、zuul 四个服务，访问 localhost:8090/consumer/sell/product ：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-consumer.png"/> </div>



## 四、错误熔断

### 4.1  默认依赖

Zuul 默认整合了 Hystrix ，不用导入其他额外依赖：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-hystrix.png"/> </div>


### 4.2 服务降级

创建 CustomZuulFallbackProvider 并实现 FallbackProvider 接口，同时用 @Component 声明为 Spring 组件，即可实现熔断时候的回退服务：

```java
/**
 * @author : heibaiying
 * @description : zuul 的熔断器
 */
@Component
public class CustomZuulFallbackProvider implements FallbackProvider {

    /*
     * 定义熔断将用于哪些路由的服务
     */
    @Override
    public String getRoute() {
        return "consumer";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            /**
             * 返回响应的 HTTP 状态代码
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            /**
             * 返回 HTTP 状态代码
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            /**
             * 返回响应的 HTTP 状态文本
             */
            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 将消息正文作为输入流返回
             */
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("商城崩溃了,请稍后重试！".getBytes());
            }

            /**
             * 将消息正文作为输入流返回
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }
}
```

正确返回了内容、同时返回的 Http 状态码也和我们设置的一样。

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-broker.png"/> </div>



## 五、Zuul  过滤器

创建自定义过滤器继承自 CustomZuulFilter，当我们访问网关的时候，如果判断 Session 中没有对应的 code，则跳转到我们自定义的登录页面：

```java
/**
 * @author : heibaiying
 * @description : 自定义 filter 过滤器
 */

@Component
public class CustomZuulFilter extends ZuulFilter {

    /**
     * 返回过滤器的类型
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 返回过滤器的优先级顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 从此方法返回“true”意味着应该调用下面的 run（）方法
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * ZuulFilter 的核心校验方法
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String code = (String)request.getSession().getAttribute("code");
        if (StringUtils.isEmpty(code)){
            // 设置值为 false 不将请求转发到对应的服务上
            currentContext.setSendZuulResponse(false);
            // 设置返回的状态码
            currentContext.setResponseStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
            HttpServletResponse response = currentContext.getResponse();
            try {
                // 跳转到登录页面
                response.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
```

**index.ftl：**

```html
<!doctype html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    <input name="username" type="text">
    <button id="btn">输入临时用户名后登录！</button>
</form>
</body>
</html>
```



## 六、负载均衡

Zuul 默认集成了 Ribbon 并实现了负载均衡，只要启动多个实例即可查看到负载均衡的效果：

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-ribbon.png"/> </div>


**这里我们直接在idea 中启动多个实例来测试：**

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-config.png"/> </div>


**负载均衡测试结果：**

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-consumer.png"/> </div>


<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-consumer-8040.png"/> </div>


<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/zuul-consumer-8030.png"/> </div>



## 七、常见异常

如果出现以下错误导致启动失败，是 Spring Boot 版本不兼容导致的错误，Finchley SR2 版本的 Zuul 组件和 Spring Boot 2.1.x 存在不兼容的情况。如果出现这个问题，则将 Spring Boot 版本降至 2.0.x 即可，本用例中采用的是 2.0.8 。

```java
APPLICATION FAILED TO START

---

Description:

The bean 'counterFactory', defined in class path resource [org/springframework/cloud/netflix/zuul/ZuulServerAutoConfiguration$ZuulCounterFactoryConfiguration.class], could not be registered. A bean with that name has already been defined in class path resource [org/springframework/cloud/netflix/zuul/ZuulServerAutoConfiguration$ZuulMetricsConfiguration.class] and overriding is disabled.

Action:

Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true

```

在实际的开发中应该严格遵循 Spring 官方的版本依赖说明：

| Release Train | Boot Version |
| ------------- | ------------ |
| Greenwich     | 2.1.x        |
| Finchley      | 2.0.x        |
| Edgware       | 1.5.x        |
| Dalston       | 1.5.x        |

更多组件的版本说明可以在 [spring cloud overview](https://spring.io/projects/spring-cloud#overview) 页面上查看。
