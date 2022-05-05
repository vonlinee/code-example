package sample.spring.boot.token.interceptor;

import com.alibaba.fastjson.JSONObject;
import sample.spring.boot.token.annotation.AuthToken;
import sample.spring.boot.token.utils.ConstVal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {

    //存放鉴权信息的Header名称，默认是Authorization
    private static final String httpHeaderName = "Authorization";

    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private static final int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = handler instanceof HandlerMethod;
        if (!flag) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthToken tokenAnnotation = method.getAnnotation(AuthToken.class);
        if (tokenAnnotation == null) {
            return true;
        }
        tokenAnnotation = handlerMethod.getBeanType().getAnnotation(AuthToken.class);
        if (tokenAnnotation == null) {
            return true;
        }
        // 如果打上了AuthToken注解则需要验证token
        String token = request.getParameter(httpHeaderName);
        log.info("Get token from request is {} ", token);
        String username = "";
        Jedis jedis = new Jedis("192.168.1.106", 6379);
        if (token != null && token.length() != 0) {
            username = jedis.get(token);
            log.info("Get username from Redis is {}", username);
        }
        if (username != null && !username.trim().equals("")) {
            //log.info("token birth time is: {}",jedis.get(username+token));
            long tokeBirthTime = Long.parseLong(jedis.get(token + username));
            log.info("token Birth time is: {}", tokeBirthTime);
            long diff = System.currentTimeMillis() - tokeBirthTime;
            log.info("token is exist : {} ms", diff);
            //重新设置Redis中的token过期时间
            if (diff > ConstVal.TOKEN_RESET_TIME) {
                jedis.expire(username, ConstVal.TOKEN_EXPIRE_TIME);
                jedis.expire(token, ConstVal.TOKEN_EXPIRE_TIME);
                log.info("Reset expire time success!");
                long newBirthTime = System.currentTimeMillis();
                jedis.set(token + username, Long.toString(newBirthTime));
            }
            //用完关闭
            jedis.close();
            request.setAttribute(REQUEST_CURRENT_KEY, username);
            return true;
        } else {
            JSONObject jsonObject = new JSONObject();
            PrintWriter out = null;
            try {
                response.setStatus(unauthorizedErrorCode);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                jsonObject.put("code", response.getStatus());
                jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                out = response.getWriter();
                out.println(jsonObject);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        }
        request.setAttribute(REQUEST_CURRENT_KEY, null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
