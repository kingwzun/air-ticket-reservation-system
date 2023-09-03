package com.atrs.airticketreservationsystem.controller;

import cn.hutool.crypto.digest.MD5;

import com.atrs.airticketreservationsystem.entity.Agent;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import com.atrs.airticketreservationsystem.entity.LoginFormData;
import com.atrs.airticketreservationsystem.service.AgentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import static com.atrs.airticketreservationsystem.common.SystemConstants.*;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentService agentService;


    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginFormData loginFormData){
        return agentService.login(loginFormData);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param agent
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             Agent agent){
        Page<Agent> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Agent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(agent.getUsername().length() > 0, Agent::getUsername, agent.getUsername());
        queryWrapper.eq(agent.getStatus().length() > 0 , Agent::getStatus, agent.getStatus());
        queryWrapper.orderByDesc(Agent::getLevel);
        Page<Agent> agentPage = agentService.page(page, queryWrapper);
        return JsonResponse.success(agentPage);
    }

    /**
     * 修改用户
     * @param agent
     * @return
     */
    @PutMapping("/updateAgent")
    public JsonResponse updateAgent(@RequestBody Agent agent){
        boolean updated = agentService.updateById(agent);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 重置密码
     * @param id
     * @return
     */
    @PutMapping("/updatePassword")
    public JsonResponse updatePassword(@RequestParam long id){
        String md5Password = MD5.create().digestHex(DEFAULT_PASSWORD);
        Agent agent = agentService.getById(id);
        agent.setPassword(md5Password);
        boolean updated = agentService.updateById(agent);
        if(!updated){
            return JsonResponse.error("重置失败");
        }
        return JsonResponse.success("密码重置成功");
    }

    /**
     * 列表删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = agentService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 新增代理
     * @param agent
     * @return
     */
    @PostMapping("/addAgent")
    public JsonResponse addAgent(@RequestBody Agent agent){
        agent.setLevel(DEFAULT_LEVEL);
        agent.setStatus(DEFAULT_STATUS);
        String md5Password = MD5.create().digestHex(DEFAULT_PASSWORD);
        agent.setPassword(md5Password);
        boolean save = agentService.save(agent);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }
}
