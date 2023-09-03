package com.atrs.airticketreservationsystem.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.atrs.airticketreservationsystem.common.RedisConstants;
import com.atrs.airticketreservationsystem.dto.UserDTO;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.atrs.airticketreservationsystem.common.RedisConstants.LOGIN_USER_KEY;

public class RefreshTokenInterception implements HandlerInterceptor {

    StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterception(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求头中的用户
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
           return true;
        }
        //2.基于token，获取redis中的用户
        Map<Object, Object> user = stringRedisTemplate.opsForHash().entries(LOGIN_USER_KEY + token);
        //3.判断用户是否存在
        if(user.isEmpty()){
            return true;
        }
        //5.将查询到的数据转为user对象
        UserDTO userDTO = BeanUtil.fillBeanWithMap(user, new UserDTO(), false);
        //6.存在，保存到ThreadLocal
        UserHolder.saveUser(userDTO);
        //7。刷新token有效期
        stringRedisTemplate.expire(LOGIN_USER_KEY + token,RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //8.放行
        return true;
    }
}
