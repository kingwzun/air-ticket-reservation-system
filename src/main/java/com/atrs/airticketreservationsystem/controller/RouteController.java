package com.atrs.airticketreservationsystem.controller;

import com.atrs.airticketreservationsystem.entity.Airport;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.Route;
import com.atrs.airticketreservationsystem.dto.RouteDTO;
import com.atrs.airticketreservationsystem.service.AirportService;
import com.atrs.airticketreservationsystem.service.RouteService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Resource
    private RouteService routeService;

    @Resource
    private AirportService airportService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param route
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             Route route) {
        Page<Route> page = new Page<>(pageNum, pageSize);
        Page<RouteDTO> pageInfo = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Route> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(route.getDepartureAirportId() != null,  Route::getDepartureAirportId, route.getDepartureAirportId());
        queryWrapper.eq(route.getDestinationAirportId() != null, Route::getDestinationAirportId, route.getDestinationAirportId());
        routeService.page(page, queryWrapper);
        BeanUtils.copyProperties(page, pageInfo,"records");
        List<Route> records = page.getRecords();
        List<RouteDTO> collect = records.stream().map((item) -> {
            RouteDTO routeDTO = new RouteDTO();
            BeanUtils.copyProperties(item, routeDTO);
            Long departureAirportId = routeDTO.getDepartureAirportId();
            Long destinationAirportId = routeDTO.getDestinationAirportId();
            //查找离开的机场
            LambdaQueryWrapper<Airport> airportLambdaQueryWrapper= new LambdaQueryWrapper<>();
            airportLambdaQueryWrapper.eq(Airport::getId, departureAirportId);
            Airport departureAirport = airportService.getOne(airportLambdaQueryWrapper);
            routeDTO.setDepartureAirport(departureAirport.getAirportName());
            //查找目的机场
            LambdaQueryWrapper<Airport> airportLambdaQueryWrapper2= new LambdaQueryWrapper<>();
            airportLambdaQueryWrapper2.eq(Airport::getId, destinationAirportId);
            Airport destinationAirport = airportService.getOne(airportLambdaQueryWrapper2);
            routeDTO.setDestinationAirport(destinationAirport.getAirportName());
            return routeDTO;
        }).collect(Collectors.toList());

        pageInfo.setRecords(collect);
        return JsonResponse.success(pageInfo);
    }

    /**
     * 根据出发机场查询以该机场为起点的航线的终点机场
     * @param id
     * @return
     */
    @GetMapping("/getByStartAirport")
    public JsonResponse getByStartAirport(@RequestParam Long id){
        LambdaQueryWrapper<Route> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Route::getDepartureAirportId, id);
        List<Route> list = routeService.list(lambdaQueryWrapper);
        List<Long> routeIds = list.stream().map(Route::getDestinationAirportId).toList();
        LambdaQueryWrapper<Airport> airportLambdaQueryWrapper = new LambdaQueryWrapper<>();
        airportLambdaQueryWrapper.in(Airport::getId, routeIds);
        List<Airport> airportList = airportService.list(airportLambdaQueryWrapper);
        return JsonResponse.success(airportList);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id) {
        boolean removeById = routeService.removeByIds(id);
        if (!removeById) {
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 更新航线
     * @param routeDTO
     * @return
     */
    @PutMapping("/updateRoute")
    public JsonResponse updateAgent(@RequestBody RouteDTO routeDTO){
        Route route = new Route();
        BeanUtils.copyProperties(routeDTO, route);
        route.setModifyTime(LocalDateTime.now());
        route.setModifier(UserHolder.getUser().getUsername());
        boolean updated = routeService.updateById(route);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 新增航线
     * @param route
     * @return
     */
    @PostMapping("/addRoute")
    public JsonResponse addAirport(@RequestBody Route route){
       route.setModifyTime(LocalDateTime.now());
       route.setPublishTime(LocalDateTime.now());
       route.setCreator(UserHolder.getUser().getUsername());
       route.setModifier(UserHolder.getUser().getUsername());
        boolean save = routeService.save(route);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }
}
