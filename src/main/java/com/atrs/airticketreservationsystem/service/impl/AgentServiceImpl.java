package com.atrs.airticketreservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.digest.MD5;
import com.atrs.airticketreservationsystem.dto.UserDTO;
import com.atrs.airticketreservationsystem.entity.Administrator;
import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.atrs.airticketreservationsystem.mapper.AgentMapper;
import com.atrs.airticketreservationsystem.service.AgentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import static com.atrs.airticketreservationsystem.common.SystemConstants.ADMIN_VIP_STATUS;
import static com.atrs.airticketreservationsystem.common.SystemConstants.AGENT_VIP_STATUS;

@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public JsonResponse login(LoginFormData loginForm) {

        String password = loginForm.getPassword();
        String account = loginForm.getAccount();
        String code = loginForm.getCode();
        String loginCode = stringRedisTemplate.opsForValue().get(loginForm.getRedisKey());
        if(loginCode == null || loginCode.length() == 0){
            return JsonResponse.error("验证码过期");
        }
        if(!code.equalsIgnoreCase(loginCode)){
            return JsonResponse.error("验证码错误");
        }
        LambdaQueryWrapper<Agent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Agent::getAccount,account);
        Agent agent = this.getOne(queryWrapper);
        if(agent == null){
            return JsonResponse.error("用户未注册");
        }
        String md5Password = MD5.create().digestHex(password);
        if(!md5Password.equals(agent.getPassword())){
            return JsonResponse.error("密码错误");
        }
        if(agent.getStatus().equals("0")){
            return JsonResponse.error("用户封禁");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(agent, userDTO);
        userDTO.setVipStatus(AGENT_VIP_STATUS);
        String token = UUID.randomUUID().toString();
        Map<String, Object> agentMap = BeanUtil.beanToMap(
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
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, agentMap);
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        return JsonResponse.success(token);
    }
}
