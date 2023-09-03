package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.PassengerInformation;
import com.atrs.airticketreservationsystem.service.PassengerInformationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/passengerInformation")
public class PassengerInformationController {

    @Resource
    private PassengerInformationService passengerInformationService;

    /**
     * 查询该用户的乘机人
     * @param id
     * @return
     */
    @GetMapping("/getByUserId")
    public JsonResponse getByUserId(Integer id){
        LambdaQueryWrapper<PassengerInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PassengerInformation::getUserId, id);
        List<PassengerInformation> list = passengerInformationService.list(queryWrapper);
        return JsonResponse.success(list);
    }

    /**
     * 新增乘机人
     * @param passengerInformation
     * @return
     */
    @PostMapping("/addPassenger")
    public JsonResponse addPassenger(@RequestBody PassengerInformation passengerInformation){
        boolean save = passengerInformationService.save(passengerInformation);
        return save ? JsonResponse.success("保存成功") : JsonResponse.error("网络异常,请稍后再试");
    }

    /**
     * 修改乘机人信息，全部修改成功才算成功，否则失败回滚
     * @param passengerInformationList
     * @return
     */
    @Transactional
    @PutMapping("/updatePassenger")
    public JsonResponse updatePassenger(@RequestBody List<PassengerInformation> passengerInformationList) {
        try {
            for (PassengerInformation passengerInformation : passengerInformationList) {
                boolean updated = passengerInformationService.updateById(passengerInformation);
                if (!updated) {
                    throw new Exception("更新乘客信息失败");
                }
            }
            return JsonResponse.success("更新乘客信息成功");
        } catch (Exception e) {
            return JsonResponse.error("更新乘客信息失败");
        }
    }

    /**
     * 删除乘机人
     * @param ids
     * @return
     */
    @DeleteMapping("/deletePassenger")
    private JsonResponse deletePassenger(@RequestParam List<Integer> ids){
        boolean removeByIds = passengerInformationService.removeByIds(ids);
        return removeByIds ? JsonResponse.success("删除成功") : JsonResponse.error("删除失败");
    }

}
