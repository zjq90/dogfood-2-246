package com.weibo.controller;

import com.weibo.common.Result;
import com.weibo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 文件上传控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${weibo.upload.path:/upload/}")
    private String uploadPath;

    @Value("${weibo.image.base-url:http://localhost:8080/upload/}")
    private String imageBaseUrl;

    /**
     * 上传图片
     */
    @PostMapping("/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("photo") MultipartFile file, HttpSession session) {
        log.info("上传图片请求");
        
        if (file.isEmpty()) {
            return Result.error("请选择文件");
        }
        
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.error("文件名无效");
        }
        
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = generateFileName() + suffix;
        
        String realPath = session.getServletContext().getRealPath(uploadPath);
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File destFile = new File(realPath + File.separator + newFileName);
        try {
            file.transferTo(destFile);
            log.info("文件上传成功: {}", destFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败");
        }
        
        String imageUrl = imageBaseUrl + newFileName;
        Map<String, String> result = new HashMap<>();
        result.put("fileName", newFileName);
        result.put("imageUrl", imageUrl);
        
        return Result.success("上传成功", result);
    }

    /**
     * 生成文件名
     */
    private String generateFileName() {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        return formatDate + random;
    }
}
