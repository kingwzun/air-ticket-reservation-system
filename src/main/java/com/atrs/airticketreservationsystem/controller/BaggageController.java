package com.atrs.airticketreservationsystem.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.atrs.airticketreservationsystem.entity.Baggage;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.service.BaggageService;

import com.atrs.airticketreservationsystem.vo.BaggageVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/baggage")
public class BaggageController {

    @Resource
    private BaggageService baggageService;

    /**
     * 删除托运订单
     * @param baggageId
     * @return
     */
    @PostMapping("/delete/{baggageId}")
    public JsonResponse delete(@PathVariable Long baggageId){
        LambdaQueryWrapper<Baggage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Baggage::getBaggageId, baggageId);
        boolean removeById = baggageService.remove(queryWrapper);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 修改托运订单
     * @param baggage
     * @return
     */
    @PutMapping("/updateBaggage")
    public JsonResponse updateAdmin(@RequestBody Baggage baggage){
        boolean updated = baggageService.updateById(baggage);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @param baggage
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             Baggage baggage){
        Page<Baggage> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Baggage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(baggage.getBaggageCode().length() > 0, Baggage::getBaggageCode, baggage.getBaggageCode());
        Page<Baggage> baggagePage = baggageService.page(page, queryWrapper);
        return JsonResponse.success(baggagePage);
    }


    /**
     * 生成托运订单
     * @param baggageVO
     * @return
     */
    @PostMapping("/saveBaggage")
    public JsonResponse saveBaggage(@RequestBody BaggageVO baggageVO){
        try {
            String ticketId = baggageVO.getTicketId();
            Long ticketId1 = Long.valueOf(ticketId);
            Baggage baggage = new Baggage();
            BeanUtils.copyProperties(baggageVO, baggage, ticketId);
            baggage.setTicketId(ticketId1);
            Snowflake snowflake = IdUtil.getSnowflake();
            long id = snowflake.nextId();
            baggage.setBaggageCode(String.valueOf(id));
            baggageService.save(baggage);
            return JsonResponse.success("托运订单生成");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
