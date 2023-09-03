package com.atrs.airticketreservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import com.atrs.airticketreservationsystem.common.RegexUtils;
import com.atrs.airticketreservationsystem.config.RabbitMQConfig;
import com.atrs.airticketreservationsystem.dto.UserDTO;
import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.mapper.UserMapper;
import com.atrs.airticketreservationsystem.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.atrs.airticketreservationsystem.common.RedisConstants.LOGIN_USER_KEY;
import static com.atrs.airticketreservationsystem.common.RedisConstants.LOGIN_USER_TTL;
import static com.atrs.airticketreservationsystem.common.SystemConstants.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     * 登录
     * @param loginFormData
     * @return
     */
    @Override
    public JsonResponse login(LoginFormData loginFormData) {
        String account = loginFormData.getAccount();
        String email = loginFormData.getEmail();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if(account != null && account.length() > 0){
            queryWrapper.eq(User::getAccount, account);
        }
        else if(email != null && email.length() > 0){
            boolean emailInvalid = RegexUtils.isEmailInvalid(email);
            if(emailInvalid){
                return JsonResponse.error("邮箱格式不正确");
            }
            queryWrapper.eq(User::getEmail, email);
        }
        else{

            String phone = loginFormData.getPhoneNumber();
            boolean phoneInvalid = RegexUtils.isPhoneInvalid(phone);
            if(phoneInvalid){
                return JsonResponse.error("手机号格式不正确");
            }
            queryWrapper.eq(User::getPhoneNumber, phone);
        }
        String password = loginFormData.getPassword();
        User user = this.getOne(queryWrapper);
        if(user == null){
            return JsonResponse.error("用户不存在");
        }
        String md5Password = MD5.create().digestHex(password);
        if(!user.getPassword().equals(md5Password)){
            return JsonResponse.error("密码错误");
        }
        if(user.getAccountStatus().equals(ACCOUNT_STATUS_BAN)){
            return JsonResponse.error("用户封禁");
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        String token = UUID.randomUUID().toString();
        Map<String, Object> userMap = BeanUtil.beanToMap(
                userDTO, new HashMap<>(), CopyOptions.create().
                        setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "0";
                            } else {
                                fieldValue = fieldValue + "";
                            }
                            return fieldValue;
                        }));
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return JsonResponse.success(token);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public JsonResponse register(User user) {
        //密码加密
        String password = user.getPassword();
        String md5Password = MD5.create().digestHex(password);
        //设置默认值
        user.setAccountStatus(DEFAULT_REGISTER_ACCOUNT_STATUS);
        user.setVipStatus(DEFAULT_ACCOUNT_VIP_LEVEL);
        user.setTotalExpenses(DEFAULT_ACCOUNT_TOTAL_EXPENSES);
        boolean saveUser = this.save(user);
        Long id = user.getId();
        if(id < 0){
            return JsonResponse.error("注册失败");
        }
        Email email = new Email("激活用户",user.getEmail(),"点击此链接完成验证:http://localhost:8081/atrs/user/activeUser/" + id);
        String jsonStr = JSONUtil.toJsonStr(email);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EMAIL_EXCHANGE, RabbitMQConfig.EMAIL_KEY,jsonStr);
        if(saveUser){
            return JsonResponse.success("注册成功");
        }
        return JsonResponse.error("注册失败");

    }
}
