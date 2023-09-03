package com.atrs.airticketreservationsystem.controller;

import cn.hutool.core.date.DateTime;
import com.atrs.airticketreservationsystem.entity.*;
import com.atrs.airticketreservationsystem.service.AnnouncementService;
import com.atrs.airticketreservationsystem.utils.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private AnnouncementService announcementService;

    /**
     * 查询公告
     * @param pageNum
     * @param pageSize
     * @param announcement
     * @return
     */
    @GetMapping("/queryAll")
    public JsonResponse page(@RequestParam(required = false, defaultValue = "1")Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10")Integer pageSize,
                             Announcement announcement){
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(announcement.getTitle().length() > 0, Announcement::getTitle, announcement.getTitle());
        queryWrapper.like(announcement.getContent().length() > 0, Announcement::getContent, announcement.getContent());
        queryWrapper.like(announcement.getPublishTime() != null , Announcement::getPublishTime, announcement.getPublishTime());
        Page<Announcement> agentPage = announcementService.page(page, queryWrapper);
        return JsonResponse.success(agentPage);
    }

    /**
     * 修改公告
     * @param announcement
     * @return
     * @throws ParseException
     */
    @PutMapping("/updateAnnouncement")
    public JsonResponse updateAnnouncement(@RequestBody Announcement announcement) throws ParseException {
        if(announcement.getTtl() != null){
            String ttl = announcement.getTtl();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ttl1 = sdf.parse(ttl);
            boolean before = ttl1.before(DateTime.now());
            if(before){
                return JsonResponse.error("如果想下架改公告请调整公告状态");
            }
        }
        announcement.setModifyTime(LocalDateTime.now());
        announcement.setModifier(UserHolder.getUser().getUsername());
        boolean updated = announcementService.updateById(announcement);
        if(!updated){
            return JsonResponse.error("修改失败");
        }
        return JsonResponse.success("修改成功");
    }

    /**
     * 删除公告
     * @param id
     * @return
     */
    @PostMapping("/announcementDelete/{id}")
    public JsonResponse deleteAnnouncement(@PathVariable Long id){
        boolean delete = announcementService.removeById(id);
        if(!delete){
            return JsonResponse.error("删除失败");
        }
        return JsonResponse.success("删除成功");
    }

    /**
     * 新增公告
     * @param announcement
     * @return
     * @throws ParseException
     */
    @PostMapping("/addAnnouncement")
    public JsonResponse addAnnouncement(@RequestBody Announcement announcement) throws ParseException {
        String ttl = announcement.getTtl();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ttl1 = sdf.parse(ttl);
        boolean before = ttl1.before(DateTime.now());
        if(before){
            return JsonResponse.error("公告结束时间不能早于或等于当前时间");
        }
        announcement.setModifyTime(LocalDateTime.now());
        announcement.setPublishTime(String.valueOf(LocalDateTime.now()));
        announcement.setCreator(UserHolder.getUser().getUsername());
        announcement.setModifier(UserHolder.getUser().getUsername());
        boolean save = announcementService.save(announcement);
        if(save){
            return JsonResponse.success("新增成功");
        }
        return JsonResponse.error("新增失败");
    }

}
