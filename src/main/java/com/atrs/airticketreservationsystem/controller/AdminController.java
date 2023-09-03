package com.atrs.airticketreservationsystem.controller;


import cn.hutool.crypto.digest.MD5;
import com.atrs.airticketreservationsystem.dto.UserDTO;
import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AdminService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import static com.atrs.airticketreservationsystem.common.SystemConstants.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;

    /**
     * 图形验证码
     * @return
     * @throws IOException
     */
    @PostMapping("/code")
    public JsonResponse code() throws IOException {
        return adminService.code();
    }

    /**
     * 登录
     * @param loginForm
     * @return
     */
    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginFormData loginForm){
        return adminService.login(loginForm);
    }

    /**
     * 获得当前登录的用户的信息
     * @return
     */
    @PostMapping("/getMe")
    public JsonResponse getMe(){
        UserDTO user = UserHolder.getUser();
        return JsonResponse.success(user);
    }

    /**
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public JsonResponse logout(){
        UserHolder.removeUser();
        return JsonResponse.success("退出成功");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public JsonResponse delete(@RequestParam List<Long> id){
        boolean removeById = adminService.removeByIds(id);
        if(!removeById){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 修改管理员
     * @param administrator
     * @return
     */
    @PutMapping("/updateAdmin")
    public JsonResponse updateAdmin(@RequestBody Administrator administrator){
        boolean updated = adminService.updateById(administrator);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 重置密码，密码重置为123123
     * @param id
     * @return
     */
    @PutMapping("/updatePassword")
    public JsonResponse updatePassword(@RequestParam long id){
        String md5Password = MD5.create().digestHex(DEFAULT_PASSWORD);
        Administrator administrator = adminService.getById(id);
        administrator.setPassword(md5Password);
        boolean updated = adminService.updateById(administrator);
        if(!updated){
            return JsonResponse.error("重置失败");
        }
        return JsonResponse.success("重置成功");
    }

    /**
     * 管理员分页
     * @param pageNum
     * @param pageSize
     * @param admin
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             Administrator admin){
        Page<Administrator> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Administrator> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(admin.getUsername().length() > 0, Administrator::getUsername, admin.getUsername());
        queryWrapper.eq(admin.getStatus().length() > 0 , Administrator::getStatus, admin.getStatus());
        queryWrapper.eq(admin.getAdministratorType().length() > 0, Administrator::getAdministratorType, admin.getAdministratorType());
        Page<Administrator> administratorPage = adminService.page(page, queryWrapper);
        return JsonResponse.success(administratorPage);
    }

    /**
     * 新增管理员
     * @param administrator
     * @return
     */
    @PostMapping("/addAdministrator")
    public JsonResponse addAdministrator(@RequestBody Administrator administrator){
        administrator.setStatus(DEFAULT_STATUS);
        String md5Password = MD5.create().digestHex(DEFAULT_PASSWORD);
        administrator.setPassword(md5Password);
        boolean save = adminService.save(administrator);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }
}
