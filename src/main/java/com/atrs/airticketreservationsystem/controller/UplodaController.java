package com.atrs.airticketreservationsystem.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
;
import com.atrs.airticketreservationsystem.entity.JsonResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.atrs.airticketreservationsystem.common.SystemConstants.IMAGE_UPLOAD_DIR;
import static com.atrs.airticketreservationsystem.common.SystemConstants.IMAGE_UPLOAD_DIR_AVATAR;


@RestController
@RequestMapping("/upload")
public class UplodaController {
    /**
     * 上传文件
     * @param image
     * @return
     */
    @PostMapping("/uploadAvatar")
    public JsonResponse uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            // 获取原始文件名称
            String originalFilename = image.getOriginalFilename();
            // 生成新文件名
            String fileName = createNewFileName(originalFilename);
            // 保存文件
            image.transferTo(new File(IMAGE_UPLOAD_DIR, fileName));
            // 返回结果
            return JsonResponse.success("http://localhost:8081/atrs" + fileName);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    /**
     * 删除文件
     * @param filename
     * @return
     */
    @GetMapping("/delete")
    public JsonResponse deleteImage(@RequestParam("name") String filename) {
        File file = new File(IMAGE_UPLOAD_DIR, filename);
        if (file.isDirectory()) {
            return JsonResponse.error("错误的文件名称");
        }
        FileUtil.del(file);
        return JsonResponse.success("删除成功");
    }

    /**
     * 创建文件
     * @param originalFilename
     * @return
     */
    private String createNewFileName(String originalFilename) {
        // 获取后缀
        String suffix = StrUtil.subAfter(originalFilename, ".", true);
        // 生成目录
        String name = UUID.randomUUID().toString();
        // 判断目录是否存在
        File dir = new File(IMAGE_UPLOAD_DIR, StrUtil.format(IMAGE_UPLOAD_DIR_AVATAR));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成文件名
        return StrUtil.format("/" + IMAGE_UPLOAD_DIR_AVATAR +"/{}.{}", name, suffix);
    }
}
