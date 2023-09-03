package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AircraftInformationService;
import com.atrs.airticketreservationsystem.service.AircraftTypeService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.atrs.airticketreservationsystem.common.SystemConstants.DEFAULT_SERVICE_YEARS;

@RestController
@RequestMapping("/aircraftInformation")
public class AircraftInformationController {
    @Resource
    private AircraftInformationService aircraftInformationService;
    @Resource
    private AircraftTypeService aircraftTypeService;

    /**
     * 飞机信息条件分页查询
     * @param pageNum
     * @param pageSize
     * @param aircraftInformation
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             AircraftInformation aircraftInformation) {
        Page<AircraftInformation> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AircraftInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(aircraftInformation.getModelId() != null, AircraftInformation::getModelId, aircraftInformation.getModelId());
        queryWrapper.like(aircraftInformation.getAircraftCode().length() > 0, AircraftInformation::getAircraftCode, aircraftInformation.getAircraftCode());
        aircraftInformationService.page(page, queryWrapper);
        List<AircraftInformation> records = page.getRecords();
        List<AircraftInformation> collect = records.stream().map((item) -> {
            AircraftInformation aircraftMsg = new AircraftInformation();
            BeanUtils.copyProperties(item, aircraftMsg);
            Long modelId = aircraftMsg.getModelId();
            //查找该飞机对应的型号名称
            LambdaQueryWrapper<AircraftType> lambdaQueryWrapper= new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(AircraftType::getId, modelId);
            AircraftType aircraftType = aircraftTypeService.getOne(lambdaQueryWrapper);
            aircraftMsg.setModel(aircraftType.getModel());
            return aircraftMsg;
        }).collect(Collectors.toList());
        page.setRecords(collect);
        return JsonResponse.success(page);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = aircraftInformationService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 新增飞机
     * @param aircraftInformation
     * @return
     */
    @PostMapping("/addPlane")
    public JsonResponse addAircraftType(@RequestBody AircraftInformation aircraftInformation){
        aircraftInformation.setServiceYears(DEFAULT_SERVICE_YEARS);
        aircraftInformation.setLastMaintenanceDate(String.valueOf(LocalDateTime.now()));
        aircraftInformation.setModifyTime(LocalDateTime.now());
        aircraftInformation.setPublishTime(LocalDateTime.now());
        aircraftInformation.setCreator(UserHolder.getUser().getUsername());
        aircraftInformation.setModifier(UserHolder.getUser().getUsername());
        boolean save = aircraftInformationService.save(aircraftInformation);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }

    /**
     * 修改飞机信息
     * @param aircraftInformation
     * @return
     */

    @PutMapping("/updatePlane")
    public JsonResponse updateAircraftType(@RequestBody AircraftInformation aircraftInformation) throws ParseException {
        String lastMaintenanceDate = aircraftInformation.getLastMaintenanceDate();
        String purchaseDate = aircraftInformation.getPurchaseDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastMaintenance = sdf.parse(lastMaintenanceDate);
        Date purchase= sdf.parse(purchaseDate);
        if(lastMaintenance.before(purchase)){
            return JsonResponse.error("上次检修日期不能早于购买日期");
        }


        
        aircraftInformation.setModifyTime(LocalDateTime.now());
        aircraftInformation.setModifier(UserHolder.getUser().getUsername());
        boolean updated = aircraftInformationService.updateById(aircraftInformation);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 查询所有飞机信息
     * @return
     */
    @GetMapping("/getAllPlane")
    public JsonResponse allAirport(){
        List<AircraftInformation> list = aircraftInformationService.list();
        return JsonResponse.success(list);
    }

    /**
     * 获取飞机对应的类型信息
     * @param id
     * @return
     */
    @GetMapping("/getModel/{id}")
    public JsonResponse getModel(@PathVariable Long id){
        LambdaQueryWrapper<AircraftInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AircraftInformation::getId, id);
        AircraftInformation aircraftInformation = aircraftInformationService.getOne(queryWrapper);
        Long modelId = aircraftInformation.getModelId();
        LambdaQueryWrapper<AircraftType> aircraftTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        aircraftTypeLambdaQueryWrapper.eq(AircraftType::getId, modelId);
        AircraftType aircraftType = aircraftTypeService.getOne(aircraftTypeLambdaQueryWrapper);
        return JsonResponse.success(aircraftType);
    }
}
