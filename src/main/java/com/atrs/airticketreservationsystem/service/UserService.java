package com.atrs.airticketreservationsystem.service;

import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.atrs.airticketreservationsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    JsonResponse login(LoginFormData loginFormData);

    JsonResponse register(User user);
}
