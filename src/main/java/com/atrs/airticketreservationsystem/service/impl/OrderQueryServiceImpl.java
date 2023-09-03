package com.atrs.airticketreservationsystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.atrs.airticketreservationsystem.config.RabbitMQConfig;
import com.atrs.airticketreservationsystem.entity.Orders;
import com.atrs.airticketreservationsystem.entity.User;
import com.atrs.airticketreservationsystem.entity.Vip;
import com.atrs.airticketreservationsystem.service.OrdersService;
import com.atrs.airticketreservationsystem.service.UserService;
import com.atrs.airticketreservationsystem.service.VipService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.atrs.airticketreservationsystem.common.RedisConstants.ORDER_MSG;
import static com.atrs.airticketreservationsystem.common.SystemConstants.*;

@Service
public class OrderQueryServiceImpl {

    @Resource
    private OrdersService ordersService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private VipService vipService;

    /**
     * 异步处理订单
     * @param msg
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    @Transactional
    public void consume(String msg) throws InterruptedException {
        Orders orders = JSONUtil.toBean(msg, Orders.class);
        //设置order数据
        orders.setOrderTime(String.valueOf(LocalDateTime.now()));
        orders.setIsUpgradeOrder(DEFAULT_IS_UPGRADE_ORDER);
        orders.setIsCancelled(DEFAULT_IS_CANCEL);
        orders.setIsUse(DEFAULT_IS_USED);
        orders.setIsUpgrade(DEFAULT_IS_UPGRADE);
        Double amount = orders.getAmount();
        //获取订票的用户
        Integer bookingPerson = orders.getBookingPerson();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        System.out.println(bookingPerson);
        queryWrapper.eq(User::getId, bookingPerson);
        User user = userService.getOne(queryWrapper);
        double expenses = user.getTotalExpenses() + orders.getAmount();
        //更新用户的总计消费
        user.setTotalExpenses(user.getTotalExpenses() + amount);
        //检查用户的vip等级是否升级
        //获取用户当前的vip等级
        Long vipStatus = user.getVipStatus();
        LambdaQueryWrapper<Vip> vipLambdaQueryWrapper = new LambdaQueryWrapper<>();
        vipLambdaQueryWrapper.eq(Vip::getLevel, vipStatus + 1);
        Vip vip = vipService.getOne(vipLambdaQueryWrapper);
        if(vip != null){
            //查询vip等级对应的所需消费
            Double requiredSpending = vip.getRequiredSpending();
            //如果消费大于该用户下一级vip的消费额
            if(expenses > requiredSpending){
                //升级用户vip等级
                user.setVipStatus(user.getVipStatus() + 1);
            }
        }
        userService.updateById(user);
        //将订单保存到redis
        String redisSaveOrderKey = ORDER_MSG + orders.getOrderId();
        Map<String, Object> orderMapById = BeanUtil.beanToMap(
                orders, new HashMap<>(), CopyOptions.create().
                        setIgnoreNullValue(true).
                        setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "";
                            } else {
                                fieldValue = fieldValue + "";
                            }
                            return fieldValue;
                        }));
        stringRedisTemplate.opsForHash().putAll(redisSaveOrderKey, orderMapById);
        ordersService.save(orders);
    }
}
