package com.atrs.airticketreservationsystem.service;

import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AgentService extends IService<Agent> {
    JsonResponse login(LoginFormData loginFormData);
}
