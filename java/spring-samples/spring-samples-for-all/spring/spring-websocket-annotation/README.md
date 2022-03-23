# Spring WebSocket（注解方式）

<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
<a href="#二实现-WebSocket">二、实现 WebSocket</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-消息处理器">2.1 消息处理器</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22--握手拦截器">2.2  握手拦截器</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-配置-WebSocket">2.3 配置 WebSocket</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-前端-WebSocket">2.4 前端 WebSocket</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#25-实现简单登录">2.5 实现简单登录</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- 项目模拟一个简单的群聊功能，为区分不同的聊天客户端，登录时候将临时用户名存储在 session 当中；
- webconfig 包是基础注解的方式配置 web，在 spring-base-annotation 项目中已经讲解过每个类作用；
- CustomHander 为消息的自定义处理器；
- CustomHandershakerInterceptor 为自定义的 websocket 的握手拦截器；
- webSocketConfig 是 websocket 的主要配置类；
- 项目以 web 的方式构建。

<div align="center"> <img src="https://gitee.com/heibaiying/spring-samples-for-all/raw/master/pictures/spring-websocket-annotation.png"/> </div>


### 1.2 基本依赖

除了基本的 Spring 依赖外，还需要导入 WebSocket 的依赖：

```xml
 <!--spring webSocket 的依赖包 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-websocket</artifactId>
    <version>5.1.3.RELEASE</version>
</dependency>
```



## 二、实现 WebSocket

### 2.1 消息处理器

继承自TextWebSocketHandler，创建消息处理类：

```java
/**
 * @description : 自定义消息处理类
 */
public class CustomHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> nameAndSession = new ConcurrentHashMap<>();

    // 建立连接时候触发
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {
        String username = getNameFromSession(session);
        nameAndSession.putIfAbsent(username, session);
    }


    // 关闭连接时候触发
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = getNameFromSession(session);
        nameAndSession.remove(username);
    }

    // 处理消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 防止中文乱码
        String msg = URLDecoder.decode(message.getPayload(), "utf-8");
        String username = getNameFromSession(session);
        // 简单模拟群发消息
        TextMessage reply = new TextMessage(username + " : " + msg);
        nameAndSession.forEach((s, webSocketSession)
                -> {
            try {
                webSocketSession.sendMessage(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private String getNameFromSession(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return (String) attributes.get(Constant.USER_NAME);
    }
}

```

### 2.2  握手拦截器

创建websocket 握手拦截器（如果没有权限拦截等需求，这一步不是必须的）：

```java
/**
 * @description : 可以按照需求实现权限拦截等功能
 */
public class CustomHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        InetAddress address = remoteAddress.getAddress();
        System.out.println(address);
        /*
         * 最后需要要显示调用父类方法，父类的 beforeHandshake 方法
         * 把 ServerHttpRequest 中 session 中对应的值拷贝到 WebSocketSession 中。
         * 如果我们没有实现这个方法，我们在最后的 handler 处理中 是拿不到 session 中的值
         * 作为测试 可以注释掉下面这一行 可以发现自定义处理器中 session 的 username 总是为空
         */
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
```

### 2.3 配置 WebSocket

```java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomHandler(), "/socket").addInterceptors(new CustomHandshakeInterceptor());
    }
}
```

### 2.4 前端 WebSocket

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.get("username")}您好！欢迎进入群聊大厅！</title>
</head>
<body>
<input id="message" type="text">
<button id="btn">发送消息</button>
<div id="show">

</div>
<script>
    let btn = document.getElementById("btn");
    let message = document.getElementById("message");
    let show = document.getElementById("show");
    let ws = new WebSocket("ws://localhost:8080/socket");
    ws.onmessage = function (evt) {
        let node = document.createElement("div");
        node.innerHTML = "<h5>" + evt.data + "</h5>";
        show.appendChild(node);
    };
    btn.addEventListener("click", function () {
        let data = message.value;
        console.log(data);
        if (data) {
            ws.send(encodeURI(data));
        } else {
            alert("请输入消息后发送");
        }
        message.value = "";
    });
    // 关闭页面时候关闭 ws
    window.addEventListener("beforeunload", function(event) {
        ws.close();
    });
</script>
</body>
</html>

```

### 2.5 实现简单登录

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input name="username" type="text">
    <button id="btn">输入临时用户名后登录！</button>
</form>
</body>
</html>
```

```java
@Controller
public class LoginController {

    @PostMapping("login")
    public String login(String username, HttpSession session){
        session.setAttribute(Constant.USER_NAME,username);
        return "chat";
    }
}
```

