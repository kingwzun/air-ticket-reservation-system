package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.Airport;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.service.AirportService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/airport")
public class AirportController {
    @Resource
    private AirportService airportService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param airport
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             Airport airport) {
        Page<Airport> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Airport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(airport.getAirportName().length() > 0, Airport::getAirportName, airport.getAirportName());
        queryWrapper.eq(airport.getProvince().length() > 0, Airport::getProvince, airport.getProvince());
        queryWrapper.eq(airport.getCity().length() > 0, Airport::getCity, airport.getCity());
        Page<Airport> airportPage= airportService.page(page, queryWrapper);
        return JsonResponse.success(airportPage);
    }
    /**
     * 查询所有机场
     * @return
     */
    @GetMapping("/getAllAirport")
    public JsonResponse allAirport(){
        List<Airport> list = airportService.list();
        return JsonResponse.success(list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id) {
        boolean removeById = airportService.removeByIds(id);
        if (!removeById) {
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 更新
     * @param airport
     * @return
     */
    @PutMapping("/updateAirport")
    public JsonResponse updateAgent(@RequestBody Airport airport){
        airport.setModifyTime(LocalDateTime.now());
        airport.setModifier(UserHolder.getUser().getUsername());
        boolean updated = airportService.updateById(airport);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 新增
     * @param airport
     * @return
     */
    @PostMapping("/addAirport")
    public JsonResponse addAirport(@RequestBody Airport airport){
        airport.setModifyTime(LocalDateTime.now());
        airport.setPublishTime(LocalDateTime.now());
        airport.setCreator(UserHolder.getUser().getUsername());
        airport.setModifier(UserHolder.getUser().getUsername());
        boolean save = airportService.save(airport);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }
}
