package com.atrs.airticketreservationsystem.controller;


import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AircraftTypeService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/aircraftType")
public class AircraftTypeController {
    @Resource
    private AircraftTypeService aircraftTypeService;

    /**
     * 飞机类型分页查询
     * @param pageNum
     * @param pageSize
     * @param aircraftType
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             AircraftType aircraftType){
        Page<AircraftType> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AircraftType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(aircraftType.getModel().length() > 0, AircraftType::getModel, aircraftType.getModel());
        Page<AircraftType> aircraftTypePage = aircraftTypeService.page(page, queryWrapper);
        return JsonResponse.success(aircraftTypePage);
    }

    /**
     * 查询所有飞机类型
     * @return
     */
    @GetMapping("/queryAllModel")
    public JsonResponse allAirport(){
        List<AircraftType> list = aircraftTypeService.list();
        return JsonResponse.success(list);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = aircraftTypeService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 新增飞机类型
     * @param aircraftType
     * @return
     */
    @PostMapping("/addAircraftType")
    public JsonResponse addAircraftType(@RequestBody AircraftType aircraftType){
        aircraftType.setModifyTime(LocalDateTime.now());
        aircraftType.setPublishTime(LocalDateTime.now());
        aircraftType.setCreator(UserHolder.getUser().getUsername());
        aircraftType.setModifier(UserHolder.getUser().getUsername());
        boolean save = aircraftTypeService.save(aircraftType);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }

    /**
     * 修改飞机类型
     * @param aircraftType
     * @return
     */
    @PutMapping("/updateAircraftType")
    public JsonResponse updateAircraftType(@RequestBody AircraftType aircraftType){
        aircraftType.setModifyTime(LocalDateTime.now());
        aircraftType.setModifier(UserHolder.getUser().getUsername());
        boolean updated = aircraftTypeService.updateById(aircraftType);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }
}
